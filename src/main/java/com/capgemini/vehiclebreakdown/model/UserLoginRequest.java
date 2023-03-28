package com.capgemini.vehiclebreakdown.model;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

public class UserLoginRequest {
	
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
