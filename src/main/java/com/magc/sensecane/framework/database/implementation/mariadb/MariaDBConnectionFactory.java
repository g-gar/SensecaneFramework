package com.magc.sensecane.framework.database.implementation.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;

public class MariaDBConnectionFactory implements ConnectionFactory<MariaDBConnectionProperties> {

	public MariaDBConnectionFactory() {
		
	}
	
	@Override
	public Connection createConnection(MariaDBConnectionProperties connectionProperties) throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		return DriverManager.getConnection(
			String.format("%s/%s", connectionProperties.getServerUri(), connectionProperties.getSchema()),
			connectionProperties.getUsername(), 
			connectionProperties.getPassword()
		);
	}

}
