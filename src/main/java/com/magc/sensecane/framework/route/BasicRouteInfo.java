package com.magc.sensecane.framework.route;

import com.magc.sensecane.framework.model.BaseEntity;

public class BasicRouteInfo extends BaseEntity {

	private final String method;
	private final String url;
	
	public BasicRouteInfo(String method, String url) {
		super();
		this.method = method;
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}
	
	
}
