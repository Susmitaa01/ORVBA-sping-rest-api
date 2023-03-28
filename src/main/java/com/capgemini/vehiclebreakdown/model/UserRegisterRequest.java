package com.capgemini.vehiclebreakdown.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

public class UserRegisterRequest {
	
	@NotNull
	private String username;
	@NotNull
	private long phoneNumber;
	@NotNull
	private String emailId;
	@NotNull
	private String userPassword;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
	
}
