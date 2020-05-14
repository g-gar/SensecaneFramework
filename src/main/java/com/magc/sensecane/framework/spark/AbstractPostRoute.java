package com.magc.sensecane.framework.spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.magc.sensecane.framework.container.Container;

import spark.Request;
import spark.Response;

public abstract class AbstractPostRoute<T> extends AbstractRoute<T> {

	public AbstractPostRoute(Container container) {
		super(container);
	}

	@Override
	public Boolean isValidRequest(Request request, Response response) throws Exception {
		if (!request.requestMethod().toUpperCase().equals("POST")) {
			response.status(500);
			throw new Exception(String.format("Not an HTTP POST request [%s]\n", request.matchedPath()));
		}
		
		return true;
	}
	
	public Map<String, String> getParams(Request request, String...keys) throws JsonMappingException, JsonProcessingException {
		Map<String, String> p = new HashMap<String, String>();
		JsonNode node = new ObjectMapper().readTree(request.body());
		String value;
		for (String key : keys) {
			if (node.has(key)) {
				p.put(key, node.get(key).asText());
			}
			else {
				throw new RuntimeException(new Exception(String.format("%s: request body does not contain parameter %s", request.pathInfo(), key)));
			}
		}
		return p;
	}

}
