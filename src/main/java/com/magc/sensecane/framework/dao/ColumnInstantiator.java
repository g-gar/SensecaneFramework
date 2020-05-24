package com.magc.sensecane.framework.dao;

public class ColumnInstantiator {

	/**
	 * String can't be null
	 * @param obj
	 * @return
	 */
	public String string(Object obj) {
		return obj != null ? obj.toString() : "";
	}
	
	public Long Long(Object obj) {
		return obj == null || obj.equals(null) || obj.toString().trim().length() == 0 || obj.toString().trim().equals("null") 
				? null 
				: Long.valueOf(obj.toString());
	}
	
	public Integer Integer(Object obj) {
		return obj == null || obj.equals(null) || obj.toString().trim().length() == 0 || obj.toString().trim().equals("null")
				? null 
				: Integer.valueOf(obj.toString());
	}
	
	public Double Double(String obj) {
		return obj == null || obj.equals(null) || obj.trim().length() == 0 || obj.trim().equals("null")
				? null 
				: Double.valueOf(obj);
	}
}
