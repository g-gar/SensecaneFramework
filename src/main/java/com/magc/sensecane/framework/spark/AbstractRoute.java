package com.magc.sensecane.framework.spark;

import java.util.logging.Logger;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.model.json.PreSerializedJson;

import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractRoute<T> implements Route {

	protected final Container container;
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	public AbstractRoute(Container container) {
		this.container = container;
	}
	
	public abstract Boolean isValidRequest(Request request, Response response) throws Exception;
	
	@Override
	public String handle(Request request, Response response) {
		
		log.info(String.format("[%s] Requested %s", request.ip(), request.pathInfo()));
		
		PreSerializedJson<T> result = null;
		
		try {
			if (isValidRequest(request, response)) {
				result = this.serve(request, response);
				response.status(200);
				response.type("application/json");
			} else {
				throw new Exception("Invalid request");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return result.toString();
	}

	public abstract PreSerializedJson<T> serve(Request request, Response response) throws Exception;
}
