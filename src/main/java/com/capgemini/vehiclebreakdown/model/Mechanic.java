package com.capgemini.vehiclebreakdown.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Mechanic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long mechanicId;
	
	@NotNull
	private String mechanicName;
	
	@NotNull
	private long mechanicPhoneNumber;
	
	@NotEmpty(message="EmailId is required")
	@Email
	private String mechanicEmailId;
	
	@NotNull
	private String mechanicLocation;
	
	@NotEmpty(message="Password is required")
	@Size(min = 5, max = 15, message="Password should be between 8-15 characters")
	private String mechanicPassword;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	
	@OneToMany(mappedBy="mechanic",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Feedback> feedback;
	
	
	public Mechanic() {
		super();
		this.role = Role.MECHANIC;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Mechanic(long mechanicId, String mechanicName, long mechanicPhoneNumber, String mechanicEmailId,
			String mechanicLocation, String mechanicPassword, List<Feedback> feedback) {
		this.mechanicId = mechanicId;
		this.mechanicName = mechanicName;
		this.mechanicPhoneNumber = mechanicPhoneNumber;
		this.mechanicEmailId = mechanicEmailId;
		this.mechanicLocation = mechanicLocation;
		this.mechanicPassword = mechanicPassword;
		this.feedback = feedback;
		this.role = Role.MECHANIC;
		
	}

	public long getMechanicId() {
		return mechanicId;
	}

	public void setMechanicId(long mechanicId) {
		this.mechanicId = mechanicId;
	}

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


	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}
	
	
}
