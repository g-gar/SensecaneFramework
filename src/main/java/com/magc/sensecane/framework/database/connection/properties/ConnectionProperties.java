package com.magc.sensecane.framework.database.connection.properties;

public interface ConnectionProperties {

	public String getServerUri();
	public String getUsername();
	public String getPassword();
	public String getSchema();
	
	public void setServerUri(String serverUri);
	public void setUsername(String username);
	public void setPassword(String password);
	public void setSchema(String schema);

}
