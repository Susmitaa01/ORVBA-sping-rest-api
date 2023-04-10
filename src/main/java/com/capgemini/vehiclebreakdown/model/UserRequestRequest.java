package com.capgemini.vehiclebreakdown.model;

public class UserRequestRequest {

	private long mechanicId;
	private String assistantType;
	private String location;

	public String getLocation() {
	    return location;
	}

	public void setLocation(String location) {
	    this.location = location;
	}

	public long getMechanicId() {
	    return mechanicId;
	}

	public void setMechanicId(long mechanicId) {
	    this.mechanicId = mechanicId;
	}

	public String getAssistantType() {
	    return assistantType;
	}

	public void setAssistantType(String assistantType) {
	    this.assistantType = assistantType;
	}
}
