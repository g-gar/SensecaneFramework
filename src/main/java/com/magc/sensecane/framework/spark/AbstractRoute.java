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
		log.info(String.format("[%s] Requested [%s] %s with body: %s", request.ip(), request.requestMethod(), request.pathInfo(), request.body()));
		PreSerializedJson<T> result = null;
		
		try {
			if (this instanceof Authenticable) {
				AuthenticationService service = container.get(AuthenticationService.class);
				String authorization = request.headers("Authorization");
				if (authorization.trim().length() == 0 || !service.validate(authorization)) {
					throw new Exception("Not authenticated");
				}
			}
			
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
		
		return result != null ? result.toString() : null;
	}

	public abstract PreSerializedJson<T> serve(Request request, Response response) throws Exception;
}
