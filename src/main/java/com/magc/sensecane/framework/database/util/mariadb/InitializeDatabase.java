package com.magc.sensecane.framework.database.util.mariadb;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.magc.sensecane.framework.database.util.ExecuteQuery;
import com.magc.sensecane.framework.database.util.json.LoadDbDdl;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.utils.AbstractUtil;
import com.magc.sensecane.framework.utils.Util;

public class InitializeDatabase extends AbstractUtil<String, Void> {

	public InitializeDatabase(Container container) {
		super(container);
	}

	@Override
	public Void execute(String filename) {
		
		Util<String, Map<String, List<String>>> loadDbDdl = new LoadDbDdl(container);
		Util<String, Boolean> tableExists = new TableExists(container);
		Util<String, ResultSet> executeQuery = new ExecuteQuery(container);
		
		Map<String, List<String>> tables = loadDbDdl.execute(filename);
		
		for (String table : tables.keySet()) {
			if (!tableExists.execute(table)) {
				for (String ddl : tables.get(table)) {
					executeQuery.execute(ddl);
				}
			}
		}
		
		return null;
	}

}
