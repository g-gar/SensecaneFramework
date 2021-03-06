package com.magc.sensecane.framework.spark;

import com.magc.sensecane.framework.container.Container;

import spark.Request;
import spark.Response;

public abstract class AbstractGetRoute<T> extends AbstractRoute<T> {

	public AbstractGetRoute(Container container) {
		super(container);
	}

	@Override
	public Boolean isValidRequest(Request request, Response response) throws Exception {

		if (!request.requestMethod().equals("GET")) {
			response.status(500);
			throw new Exception(String.format("Not an HTTP GET request [%s]\n", request.matchedPath()));
		}
		
		return true;
	}

}
