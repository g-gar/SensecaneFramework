package com.magc.sensecane.framework.model.json;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.magc.sensecane.framework.model.database.TableEntity;

public class PreSerializedJson<T> implements Map<String, Object> {

	private Map<String, Object> results;
	
	public PreSerializedJson() {
		results = new HashMap<String, Object>();
	}
	
	public PreSerializedJson(T obj) {
		this(obj, new String[0]);
	}
	
	public PreSerializedJson(T obj, String...fields) {
		results = extractParams(obj, fields);
	}
		
	public PreSerializedJson(Collection<T> obj, String...fields) {
		results = new HashMap<String, Object>() {
			{
				int i = 0;
				for (final Iterator<T> it = obj.iterator(); it.hasNext();) {
					put(""+(i++), extractParams(it.next(), fields));
				}
			}
		};
	}
	
	public Map<String, Object> extractParams(T obj, String...fields) {
		Map<String, Object> results = new HashMap<String, Object>();
		List<String> excluded = Arrays.asList(fields);
		boolean accesible;
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (excluded.contains(field.getName()) || (fields.length==1 && fields[0].equals("*"))) {
				try {
					accesible = field.isAccessible();
					field.setAccessible(true);
					results.put(field.getName(), field.get(obj));
					field.setAccessible(accesible);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return results;
	}

	@Override
	public int size() {
		return results.size();
	}

	@Override
	public boolean isEmpty() {
		return results.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return results.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return results.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return results.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return results.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return results.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		results.putAll(m);
	}

	@Override
	public void clear() {
		results.clear();
	}

	@Override
	public Set<String> keySet() {
		return results.keySet();
	}

	@Override
	public Collection<Object> values() {
		return results.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return results.entrySet();
	}

	@Override
	public String toString() {
		String str = null;
		
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			str = ow.writeValueAsString(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
}
