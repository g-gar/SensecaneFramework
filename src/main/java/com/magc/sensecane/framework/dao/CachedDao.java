package com.magc.sensecane.framework.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;
import com.magc.sensecane.framework.exception.InstanceNotFoundException;
import com.magc.sensecane.framework.model.database.TableEntity;
import com.magc.sensecane.framework.model.database.annotation.Autogenerated;
import com.magc.sensecane.framework.model.database.annotation.Column;
import com.magc.sensecane.framework.model.database.annotation.Table;
import com.magc.sensecane.framework.utils.DaoUtils;

public abstract class CachedDao<T extends TableEntity<Integer>> extends ColumnInstantiator implements Dao<T> {
	
	protected ConnectionPool pool;
	protected final Map<Integer, T> cache;
	protected Class<T> clazz;
	
	public CachedDao(ConnectionPool pool, Class<T> clazz) {
		this.pool = pool;
		this.cache = new ConcurrentHashMap<Integer, T>();
		this.clazz = clazz;
	}
	
	public void empty() {
		for (Integer e : this.cache.keySet()) {
			this.cache.remove(e);
		}
	}

	@Override
	public Boolean contains(T entity) throws InstanceNotFoundException {
		return entity.getId() != null && this.cache.containsKey(entity.getId());
	}

	@Override
	public T find(Integer id) {
		return this.cache.get(id);
	}

	@Override
	public List<T> findAll() {
		return this.cache.values().stream().collect(Collectors.toList());
	}

	@Override
	public T insertOrUpdate(T entity) {
		T result = null;
		if (entity != null) {
			if (entity.getId() == null) {
				result = insert(entity);
			} else if (this.find(entity.getId()) != null ) {
				result = update(entity);
			}
		}
		return result;
	}
	
	protected T insert(T entity) {
		T result = null;
		Field pk = entity.getPrimaryKeyField();
		List<Field> columns = entity.getColumnFields().stream().filter(e -> e!=pk && e.getDeclaredAnnotationsByType(Autogenerated.class).length == 0 ).collect(Collectors.toList());
		StringBuilder sb = new StringBuilder("insert into ").append(entity.getTablename()).append("( ");
		
		columns.stream().forEach(f -> {
			Column col = f.getDeclaredAnnotation(Column.class);
			sb.append(col.value());
			sb.append(columns.indexOf(f) < columns.size() - 1 ? ", " : " ");
		});
		
		sb.append(") values( ");
		
		columns.stream().forEach(f -> {
			try {
				boolean accesible = f.isAccessible();
				f.setAccessible(true);
				sb.append(f.getType().equals(String.class)?"'":"");
				sb.append(f.get(entity));
				sb.append(f.getType().equals(String.class)?"'":"");
				sb.append(columns.indexOf(f) < columns.size() - 1 ? ", " : " ");
				f.setAccessible(accesible);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		sb.append(")");
		
		System.out.println(sb.toString());
		
		Connection connection = pool.get();
		try(PreparedStatement ps = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS)) {
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (pk.getDeclaredAnnotation(Autogenerated.class)!=null && !rs.next()) throw new SQLException("JDBC driver did not return generated key.");
			
			List<String> str = new ArrayList<String>();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			if (pk.getDeclaredAnnotation(Autogenerated.class)!=null) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) str.add(rs.getString(i));
			}
			
			for (int i = 0; i < columns.size(); i++) {
				Field f = columns.get(i);
				boolean accesible = f.isAccessible();
				f.setAccessible(true);
				str.add(f.get(entity)==null?(String) f.get(entity):f.get(entity).toString());
				f.setAccessible(accesible);
			}
			
			T temp = createInstance(str.toArray(new String[str.size()]));
			this.cache.put(temp.getId(), temp);
			result = this.find(temp.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		pool.release(connection);
		return result;
	}
	
	protected T update(T entity) {
		Field pk = entity.getPrimaryKeyField();
		List<Field> columns = entity.getColumnFields().stream().filter(e -> e!=pk).collect(Collectors.toList());

		StringBuilder sb = new StringBuilder("update ").append(entity.getTablename()).append(" set ");
		columns.stream().forEach(f -> {
			try {
				Column col = f.getDeclaredAnnotation(Column.class);
				boolean accesible = f.isAccessible();
				f.setAccessible(true);
				sb.append(col.value())
					.append(" = ")
					.append(f.getType().equals(String.class)?"'":"")
					.append(entity!=null
						? f.get(entity) != null 
							? f.get(entity).toString()
							: null
						: null
					)
					.append(f.getType().equals(String.class)?"'":"")
					.append(columns.indexOf(f) < columns.size() - 1 ? ", " : " ");
				f.setAccessible(accesible);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		sb.append("where ")
			.append(pk.getDeclaredAnnotation(Column.class).value())
			.append(" = ")
			.append(entity.getId());
		
		System.out.println(sb.toString());

		Connection connection = pool.get();
		try(PreparedStatement ps = connection.prepareStatement(sb.toString())) {
			int updatedRows = ps.executeUpdate();
			if (updatedRows == 0) {
				throw new InstanceNotFoundException(entity.getId(), clazz);
			}
			this.cache.put(entity.getId(), entity);
		} catch (SQLException | InstanceNotFoundException e) {
			e.printStackTrace();
		}
		pool.release(connection);
		
		return this.cache.get(entity.getId());
	}

	@Override
	public T remove(T entity) {
		T result = null;
		
		String sql = String.format("delete from %s where %s = ?", entity.getTablename(), entity.getPrimaryKeyColumn().value());
		Connection connection = pool.get();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, entity.getId());
			int removedRows = ps.executeUpdate();
			if (removedRows == 0) throw new InstanceNotFoundException(entity.getId(), entity.getClass());
			else {
				Integer temp = this.cache.keySet().stream()
					.filter(e -> e.equals(entity.getId()))
					.findFirst().orElse(null);
				if (temp != null) {
					result = this.cache.remove(temp);
				}
			}
		} catch (SQLException | InstanceNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			pool.release(connection);
		}
		
		return result;
	}

	@Override
	public List<T> removeAll() {
		return this.cache.values().stream().map(this::remove).collect(Collectors.toList());
	}
	
	@Override
	public void truncate() {
		String sql = String.format("truncate %s", clazz.getDeclaredAnnotationsByType(Table.class)[0].value());
		Connection connection = pool.get();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pool.release(connection);
		}
	}

	public void reloadCache() {
		String sql = String.format("select * from %s", clazz.getDeclaredAnnotationsByType(Table.class)[0].value());
		Connection connection = pool.get();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			DaoUtils.parseResultSetAs(rs, clazz).forEach(entity -> CachedDao.this.cache.put(entity.getId(), entity));
//			while (rs.next()) {
//				T temp = createInstance(rs, clazz);
//				cache.put(temp.getId(), temp);
//			}
		} catch (SQLException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			pool.release(connection);
		}
	}
	
	public abstract T createInstance(String...parameters);
	
	public T createInstance(ResultSet rs, Class<T> clazz) throws InstantiationException, IllegalAccessException, SQLException, IllegalArgumentException, InvocationTargetException, SecurityException {
		T a = (T) clazz.newInstance();
		
		List<String> str = new ArrayList<String>();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) str.add(rs.getString(i));
		for (int i = 0; i < a.getColumnFields().size(); i++) {
			Field f = a.getColumnFields().get(i);
			boolean accesible = f.isAccessible();
			f.setAccessible(true);
			str.add((String) f.get(a));
			f.setAccessible(accesible);
		}
		
		return createInstance(str.toArray(new String[str.size()]));
	}
}