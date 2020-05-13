package com.magc.sensecane.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.text.CaseUtils;

import com.magc.sensecane.framework.model.BaseEntity;

public class DaoUtils {

	public static <T extends BaseEntity> Set<T> parseResultSetAs(ResultSet rs, Class<T> clazz) {
		Set<T> list = null;
		ResultSetMetaData rsmd;
		Constructor<T> constructor;
		List<Field> fields;
		Field field;
		int fieldIndex;
		T instance;
		Class[] parameterClasses;
		Object[] parameters;
		int columnCount;
		String columnName;
		
		try {
			if (rs != null) {
				list = new HashSet<T>();
				rsmd = rs.getMetaData();
				columnCount = rsmd.getColumnCount();
				fields = Arrays.asList(clazz.getDeclaredFields());
				
				while (rs.next()) {
					parameters = new Object[columnCount];
					parameterClasses = new Class[columnCount];
					
					for (int i = 1; i <= columnCount; i++) {		
						columnName = CaseUtils.toCamelCase(rsmd.getColumnName(i), false, '_');
						field = clazz.getDeclaredField(columnName);
						fieldIndex = fields.indexOf(field);
						
						String temp = rsmd.getColumnClassName(i);
						switch (temp) {
							case "java.math.BigDecimal": 
								temp = "java.lang.Double";
								parameters[i-1] = Double.valueOf(rs.getString(i));
								break;
							case "java.math.BigInteger": 
								temp = "java.lang.Long";
								parameters[i-1] = Long.valueOf(rs.getString(i));
								break;
							default:
								parameters[i-1] = rs.getObject(i);
						}
						parameterClasses[i-1] = Class.forName(temp);
					}
					
					constructor = clazz.getConstructor(parameterClasses);
					instance = constructor.newInstance(parameters);
					list.add(instance);
				}
			}
		} catch (Exception e) {
			System.out.println("Error parsing rs to class:  " + clazz);
			e.printStackTrace();
		}
		
		return list;
	}
	
}
