package com.magc.sensecane.framework.database.util.mariadb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.magc.sensecane.framework.database.MariaDBConnectionProperties;
import com.magc.sensecane.framework.database.util.ExecuteQuery;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;
import com.magc.sensecane.framework.utils.AbstractUtil;
import com.magc.sensecane.framework.utils.Util;

public class TableExists extends AbstractUtil<String, Boolean> {

	public TableExists(Container container) {
		super(container);
	}

	@Override
	public Boolean execute(String table) {
		MariaDBConnectionProperties properties = (MariaDBConnectionProperties) container.get(ConnectionProperties.class);

		String schema = properties.getSchema();
		String query = String.format(
				"SELECT * FROM information_schema.tables WHERE table_schema = '%s'  AND table_name = '%s' LIMIT 1;",
				schema, table);

		Util<String, ResultSet> executeQueryUtil = new ExecuteQuery(container);

		int i = 0;
		try {
			ResultSet rs = executeQueryUtil.execute(query);
			rs.last();
		    i = rs.getRow();
		    rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i > 0;
	}

}
