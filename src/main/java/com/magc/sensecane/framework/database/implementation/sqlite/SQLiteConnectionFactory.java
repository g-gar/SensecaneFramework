package com.magc.sensecane.framework.database.implementation.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;

public class SQLiteConnectionFactory implements ConnectionFactory<SQLiteConnectionProperties> {

	@Override
	public Connection createConnection(SQLiteConnectionProperties connectionProperties) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(String.format("jdbc:sqlite:%s", connectionProperties.getServerUri()));
	}

}
