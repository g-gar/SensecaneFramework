package com.magc.sensecane.framework.spark;

public interface AuthenticationService {

	boolean validate(String authorizationHeader) throws Exception;
	
}
