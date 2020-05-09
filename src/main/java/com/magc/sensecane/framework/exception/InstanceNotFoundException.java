package com.magc.sensecane.framework.exception;

public class InstanceNotFoundException extends Exception {

	public InstanceNotFoundException(Integer id, Class clazz) {
		super(String.format("Instance not found %s.%s", clazz, id));
	}

}
