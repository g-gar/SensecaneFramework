package com.magc.sensecane.framework.database.implementation.mariadb;

import com.magc.sensecane.framework.database.connection.properties.ConnectionProperties;
import com.magc.sensecane.framework.model.BaseEntity;

public class MariaDBConnectionProperties extends BaseEntity implements ConnectionProperties {
	
	private String serverUri;
	private String username;
	private String password;
	private String schema;
	
	public MariaDBConnectionProperties(String serverUri, String username, String password, String schema) {
		this.serverUri = serverUri;
		this.username = username;
		this.password = password;
		this.schema = schema;
	}

	public String getServerUri() {
		return serverUri;
	}

	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
