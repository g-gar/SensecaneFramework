package com.magc.sensecane.framework.spark.util;

import static com.magc.sensecane.framework.spark.util.RequestUtil.*;

import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters {

	// If a user manually manipulates paths and forgets to add
	// a trailing slash, redirect the user to the correct path
	public static Filter addTrailingSlashes = (Request request, Response response) -> {
		if (!request.pathInfo().endsWith("/")) {
			response.redirect(request.pathInfo() + "/");
		}
	};

	// Locale change can be initiated from any page
	// The locale is extracted from the request and saved to the user's session
	public static Filter handleLocaleChange = (Request request, Response response) -> {
		if (getQueryLocale(request) != null) {
			request.session().attribute("locale", getQueryLocale(request));
			response.redirect(request.pathInfo());
		}
	};

	// Enable GZIP for all responses
	public static Filter addGzipHeader = (Request request, Response response) -> {
		response.header("Content-Encoding", "gzip");
	};
	
	public static Filter addCorsHeader = (request, response) -> {
	    response.header("Access-Control-Allow-Origin", "*");
	};
	
	public static Filter acceptJson = (request, response) -> {
		response.header("Accept", "application/json");
	};
	
	public static Filter returnsJson = (request, response) -> {
		response.header("Content-Type", "application/json");
	};
	
	public static Filter postAcceptsJson = (request, response) -> {
		if (request.requestMethod().equals("POST")) {
			acceptJson.handle(request, response);
		}
	};
	
	public static Filter setContentlength = (request, response) -> {
		response.header("Content-Length", "" + response.body().length());
	};

}
