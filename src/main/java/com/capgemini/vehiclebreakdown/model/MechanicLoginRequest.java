package com.capgemini.vehiclebreakdown.model;

public class MechanicLoginRequest {
		
	private String username;
	private String password;
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
}
