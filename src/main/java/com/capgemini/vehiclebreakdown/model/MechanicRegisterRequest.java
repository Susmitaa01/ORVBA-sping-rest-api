package com.capgemini.vehiclebreakdown.model;

import javax.validation.constraints.NotNull;

public class MechanicRegisterRequest {

	@NotNull
	private String mechanicName;
	@NotNull
	private long mechanicPhoneNumber;
	@NotNull
	private String mechanicEmailId;
	@NotNull
	private String mechanicLocation;
	@NotNull
	private String mechanicPassword;
	
	public String getMechanicName() {
		return mechanicName;
	}
	public void setMechanicName(String mechanicName) {
		this.mechanicName = mechanicName;
	}
	public long getMechanicPhoneNumber() {
		return mechanicPhoneNumber;
	}
	public void setMechanicPhoneNumber(long mechanicPhoneNumber) {
		this.mechanicPhoneNumber = mechanicPhoneNumber;
	}
	public String getMechanicEmailId() {
		return mechanicEmailId;
	}
	public void setMechanicEmailId(String mechanicEmailId) {
		this.mechanicEmailId = mechanicEmailId;
	}
	public String getMechanicLocation() {
		return mechanicLocation;
	}
	public void setMechanicLocation(String mechanicLocation) {
		this.mechanicLocation = mechanicLocation;
	}
	public String getMechanicPassword() {
		return mechanicPassword;
	}
	public void setMechanicPassword(String mechanicPassword) {
		this.mechanicPassword = mechanicPassword;
	}
	
	
}
