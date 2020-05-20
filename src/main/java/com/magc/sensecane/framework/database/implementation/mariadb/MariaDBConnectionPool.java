package com.magc.sensecane.framework.database.implementation.mariadb;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.pool.AbstractConnectionPool;

public class MariaDBConnectionPool extends AbstractConnectionPool {
	
	public MariaDBConnectionPool(Container container) {
		super(container);
	}

}
