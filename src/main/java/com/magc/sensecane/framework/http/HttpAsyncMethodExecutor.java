package com.magc.sensecane.framework.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;

public abstract class HttpAsyncMethodExecutor<R> {
	
	public void fetch(HttpRequestBase request, BiConsumer<? extends HttpAsyncMethodExecutor<R>, HttpResponse> completed)
			throws ClientProtocolException, IOException {
		this.fetch(request, completed, (executor, exception) -> exception.printStackTrace());
	}
	
	public <T extends HttpAsyncMethodExecutor<R>> void fetch(HttpRequestBase request, BiConsumer<T, HttpResponse> completed, BiConsumer<T, Exception> error) throws ClientProtocolException, IOException {
		Thread t = new Thread(() -> {
	        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.custom().setConnectionManager(new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor())).build()) {
	        	httpClient.start();
	        	final Future<HttpResponse> future = httpClient.execute(request, new FutureCallback<HttpResponse>() {
					@Override public void failed(Exception e) {
						error.accept((T) HttpAsyncMethodExecutor.this, e);
					}
					
					@Override public void cancelled() {}
					
					@Override public void completed(HttpResponse response) {
						completed.accept((T) HttpAsyncMethodExecutor.this, response);
					}
				});
	        	future.get();
	        } catch (InterruptedException | ExecutionException | IOException e) {
				e.printStackTrace();
			}
		});
		t.start();
	}

	public abstract R parseResponse(HttpResponse response);
}
