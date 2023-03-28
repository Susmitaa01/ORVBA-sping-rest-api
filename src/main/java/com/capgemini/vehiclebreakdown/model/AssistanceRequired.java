package com.capgemini.vehiclebreakdown.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="assistance_required")
public class AssistanceRequired {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long assistanceId;
	
	@NotEmpty(message="Assistance on What required?")
	private String assistanceType;

	@NotNull
	private long userId;
	
	@NotNull
	private long mechanicId;

	@NotNull
	private String location;
	
	@OneToOne(mappedBy="assiatnceRequired",cascade = CascadeType.MERGE)
	@JsonIgnore
	private Feedback feedback;

	public AssistanceRequired() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssistanceRequired(long assistanceId,
			@NotEmpty(message = "Assistance on What required?") String assistanceType, @NotNull long userId,
			@NotNull long mechanicId, @NotNull String location, Feedback feedback) {
		super();
		this.assistanceId = assistanceId;
		this.assistanceType = assistanceType;
		this.userId = userId;
		this.mechanicId = mechanicId;
		this.location = location;
		this.feedback = feedback;
	}

	public long getAssistanceId() {
		return assistanceId;
	}

	public void setAssistanceId(long assistanceId) {
		this.assistanceId = assistanceId;
	}

	public String getAssistanceType() {
		return assistanceType;
	}

	public void setAssistanceType(String assistanceType) {
		this.assistanceType = assistanceType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMechanicId() {
		return mechanicId;
	}

	public void setMechanicId(long mechanicId) {
		this.mechanicId = mechanicId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "AssistanceRequired [assistanceId=" + assistanceId + ", assistanceType=" + assistanceType + ", userId="
				+ userId + ", mechanicId=" + mechanicId + ", location=" + location + ", feedback=" + feedback + "]";
	}
	
	
	
}
