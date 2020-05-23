package com.magc.sensecane.framework.http;

import java.io.IOException;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;

public interface HttpMethod<R> {
	
	R parseResponse(HttpEntity entity);
	HttpResponse fetch(HttpRequestBase request, Consumer<HttpResponse> consumer)
			throws ClientProtocolException, IOException;
	
}
