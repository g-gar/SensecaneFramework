package com.magc.sensecane.framework.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;

public abstract class HttpAsyncMethodExecutor<R> implements HttpMethod<R> {

	private final Consumer<R> completed;

	public HttpAsyncMethodExecutor() {
		this.completed = null;
	}

	public HttpAsyncMethodExecutor(Consumer<R> consumer) {
		this.completed = consumer;
	}

	@Override public R fetch(HttpRequestBase request) throws ClientProtocolException, IOException {
		Thread t = new Thread(() -> {
	        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setConnectionManager(new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor())).build()) {
	        	httpClient.start();
	        	final Future<HttpResponse> future = httpClient.execute(request, new FutureCallback<HttpResponse>() {
					@Override public void failed(Exception e) {e.printStackTrace();}
					@Override public void cancelled() {}
					
					@Override public void completed(HttpResponse response) {
						Consumer<R> c = HttpAsyncMethodExecutor.this.completed;
						if (c != null) {
							c.accept(parseResponse(response.getEntity()));
						}
					}
				});
	        	future.get();
	        } catch (InterruptedException | ExecutionException | IOException e) {
				e.printStackTrace();
			}
		});
		t.start();
		return null;
	}

	public abstract R parseResponse(HttpEntity entity);
}
