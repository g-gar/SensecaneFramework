package com.magc.sensecane.framework.database.implementation.sqlite;

import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;

public class SQLiteConnectionProperties implements ConnectionProperties {

	private String serverUri;
	
	public SQLiteConnectionProperties(String serverUri) {
		this.serverUri = serverUri;
	}
	
	@Override
	public String getServerUri() {
		return serverUri;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getSchema() {
		return null;
	}

	@Override
	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	@Override
	public void setUsername(String username) {
	}

	@Override
	public void setPassword(String password) {
	}

	@Override
	public void setSchema(String schema) {
	}

}
