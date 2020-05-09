package com.magc.sensecane.framework.database.util.json;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.utils.AbstractUtil;

public class LoadDbDdl extends AbstractUtil<String, Map<String, List<String>>> {

	public LoadDbDdl(Container container) {
		super(container);
	}

	@Override
	public Map<String, List<String>> execute(String filename) {
		Map<String, List<String>> tables = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson;
		Type type;
		
		try  (Reader reader = new FileReader(filename)) {
			gson = builder.create();
			type = new TypeToken<Map<String, List<String>>>() {}.getType();
			
			tables = gson.fromJson(reader, type);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tables;
	}

}
