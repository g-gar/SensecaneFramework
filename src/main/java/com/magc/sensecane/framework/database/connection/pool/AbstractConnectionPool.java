package com.magc.sensecane.framework.database.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import java.util.stream.IntStream;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.factory.ConnectionFactory;
import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;

public class AbstractConnectionPool implements ConnectionPool {

	private Stack<Connection> connections;
	private final Container container;
	
	public AbstractConnectionPool(Container container) {
		this.container = container;
		this.connections = null;
	}
	
	public Connection get() {
		return connections.pop();
	}

	public boolean release(Connection connection) {
		return connections.add(connection);
	}

	public void shutdown() throws SQLException {
		for (Connection connection : connections) {
			connection.close();
		}
		connections.empty();
	}

	@Override
	public void configure(int maxAvailableConnections) {
		ConnectionFactory factory = container.get(ConnectionFactory.class);
		ConnectionProperties properties = container.get(ConnectionProperties.class);
		
		this.connections = new Stack<Connection>() {
			{
				IntStream.range(0, maxAvailableConnections).forEach(i -> {
					try {
						add(factory.createConnection(properties));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
			}
		};
	}

}
