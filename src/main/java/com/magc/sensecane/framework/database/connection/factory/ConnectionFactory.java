package com.magc.sensecane.framework.database.connection.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;

public interface ConnectionFactory<T extends ConnectionProperties> {

	Connection createConnection(T connectionProperties) throws SQLException, ClassNotFoundException;
	
}
