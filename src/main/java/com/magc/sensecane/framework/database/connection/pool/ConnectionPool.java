package com.magc.sensecane.framework.database.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

	public void configure(int n);
	public Connection get();
	public boolean release(Connection connection);
	public void shutdown() throws SQLException;
	
}
