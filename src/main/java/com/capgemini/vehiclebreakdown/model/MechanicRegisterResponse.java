package com.capgemini.vehiclebreakdown.model;

public class MechanicRegisterResponse {
	
	private boolean status;
	private String message;
	private Mechanic mechanic;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Mechanic getMechanic() {
		return mechanic;
	}
	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}
	
	
}
