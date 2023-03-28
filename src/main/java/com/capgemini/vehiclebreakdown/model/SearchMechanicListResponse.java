package com.capgemini.vehiclebreakdown.model;

import java.util.List;

public class SearchMechanicListResponse {
		
	private boolean status;
	private String message;
	private List<Mechanic> mechanic_list;
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
	public List<Mechanic> getMechanic_list() {
		return mechanic_list;
	}
	public void setMechanic_list(List<Mechanic> mechanic_list) {
		this.mechanic_list = mechanic_list;
	}
	
	
}
