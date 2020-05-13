package com.magc.sensecane.framework.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.magc.sensecane.framework.dao.Dao;
import com.magc.sensecane.framework.model.BaseEntity;

public class DaoContainer {
	
	private final Map<Class<? extends BaseEntity>, Dao> cache;
	
	public DaoContainer() {
		this.cache = new HashMap<Class<? extends BaseEntity>, Dao>();
	}

	public <T extends BaseEntity> T register(Class<? extends BaseEntity> clazz, Dao<T> entity) {
		return (T) this.cache.put(clazz, (Dao<BaseEntity>) entity);
	}

	public <T extends BaseEntity> T unregister(Class<? extends BaseEntity> clazz) {
		return (T) (containsClass(clazz) ? this.register(clazz, null) : null);
	}

	public <T extends BaseEntity> T unregister(Class<? extends BaseEntity> clazz, T entity) {
		return (T) (containsClass(clazz) ? this.cache.remove(clazz) : null);
	}

	public Boolean containsClass(Class<? extends BaseEntity> clazz) {
		return this.cache.containsKey(clazz);
	}

	public <T extends BaseEntity> Dao<T> get(Class<T> clazz) {
		return (Dao<T>) this.cache.get(clazz);
	}
	
	public Set<Class<? extends BaseEntity>> getKeys() {
		return this.cache.keySet();
	}

}
