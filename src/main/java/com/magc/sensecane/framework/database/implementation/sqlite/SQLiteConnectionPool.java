package com.magc.sensecane.framework.database.implementation.sqlite;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.pool.AbstractConnectionPool;

public class SQLiteConnectionPool extends AbstractConnectionPool {

	public SQLiteConnectionPool(Container container) {
		super(container);
	}

}
