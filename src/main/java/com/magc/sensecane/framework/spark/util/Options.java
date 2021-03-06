package com.magc.sensecane.framework.spark.util;

import spark.Request;
import spark.Response;
import spark.Route;

public class Options {

	public static Route enableCors = (Request request, Response response) -> {
    	String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers",  accessControlRequestHeaders);
        }

        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        }
        
        return "OK";
    };
	
}
