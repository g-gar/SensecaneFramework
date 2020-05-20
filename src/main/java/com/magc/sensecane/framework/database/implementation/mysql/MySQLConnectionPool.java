package com.magc.sensecane.framework.database.implementation.mysql;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.pool.AbstractConnectionPool;

public class MySQLConnectionPool extends AbstractConnectionPool {
	
	public MySQLConnectionPool(Container container) {
		super(container);
	}

}
