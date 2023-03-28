package com.capgemini.vehiclebreakdown.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long feedbackId;
	
	@NotNull
	private long userId;
	@NotEmpty(message="FeedbackMessage is required")
	private String feedbackMessage;
	
	@NotNull
	@Min(value = 0, message = "Ratings not given")
    @Max(value = 5, message = "Ratings can be given from 1 to 5 only ")
	private int ratings;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mechanicId")
	@JsonIgnore
	private Mechanic mechanic;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assistanceId")
	@JsonIgnore
	private AssistanceRequired assiatnceRequired;

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(long feedbackId, @NotNull long userId,
			@NotEmpty(message = "FeedbackMessage is required") String feedbackMessage,
			@NotNull @Min(value = 0, message = "Ratings not given") @Max(value = 5, message = "Ratings can be given from 1 to 5 only ") int ratings,
			Mechanic mechanic, AssistanceRequired assiatnceRequired) {
		super();
		this.feedbackId = feedbackId;
		this.userId = userId;
		this.feedbackMessage = feedbackMessage;
		this.ratings = ratings;
		this.mechanic = mechanic;
		this.assiatnceRequired = assiatnceRequired;
	}

	public long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFeedbackMessage() {
		return feedbackMessage;
	}

	public void setFeedbackMessage(String feedbackMessage) {
		this.feedbackMessage = feedbackMessage;
	}

	public int getRatings() {
		return ratings;
	}

	public void setRatings(int ratings) {
		this.ratings = ratings;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public AssistanceRequired getAssiatnceRequired() {
		return assiatnceRequired;
	}

	public void setAssiatnceRequired(AssistanceRequired assiatnceRequired) {
		this.assiatnceRequired = assiatnceRequired;
	}
	
	
}
