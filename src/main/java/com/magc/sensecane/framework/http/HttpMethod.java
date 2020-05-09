package com.magc.sensecane.framework.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;

public interface HttpMethod<R> {
	
	R fetch(HttpRequestBase request) throws ClientProtocolException, IOException;
	R parseResponse(HttpEntity entity);
	
}
