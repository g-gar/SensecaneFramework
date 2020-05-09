package com.magc.sensecane.framework.database.util.sqlite;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.utils.AbstractUtil;

public class InitializeDatabase extends AbstractUtil<String, Boolean> {

	public InitializeDatabase(Container container) {
		super(container);
	}

	@Override
	public Boolean execute(String table) {
		return null;
	}

}
