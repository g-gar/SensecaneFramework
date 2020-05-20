package com.magc.sensecane.framework.database.implementation.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;

public class MySQLConnectionFactory implements ConnectionFactory<MySQLConnectionProperties> {

	public MySQLConnectionFactory() {
		
	}
	
	@Override
	public Connection createConnection(MySQLConnectionProperties connectionProperties) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
			String.format("%s/%s", connectionProperties.getServerUri(), connectionProperties.getSchema()),
			connectionProperties.getUsername(), 
			connectionProperties.getPassword()
		);
	}

}
