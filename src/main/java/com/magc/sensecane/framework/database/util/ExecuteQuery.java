package com.magc.sensecane.framework.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.pool.ConnectionPool;
import com.magc.sensecane.framework.utils.AbstractUtil;

public class ExecuteQuery extends AbstractUtil<String, ResultSet> {

	public ExecuteQuery(Container container) {
		super(container);
	}

	@Override
	public ResultSet execute(String sql) {
		ConnectionPool pool = container.get(ConnectionPool.class);
		Connection connection = pool.get();
		ResultSet result = null;
		try (PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			connection.setAutoCommit(false);
			result = ps.executeQuery();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e);
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		pool.release(connection);
		
		return result;
	}
}
