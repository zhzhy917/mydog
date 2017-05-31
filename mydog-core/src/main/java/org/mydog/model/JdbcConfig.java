package org.mydog.model;

import java.io.Serializable;

public class JdbcConfig implements Serializable{
	
	private static final long serialVersionUID = -8403061531438484404L;


	private String driverClass ; 
	
	private String username ; 
	private String connectionUrl ;
	
	private String password ;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
