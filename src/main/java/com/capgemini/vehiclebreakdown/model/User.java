package com.capgemini.vehiclebreakdown.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="user_details")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@NotEmpty(message="Username is required")
	private String userName;
	
	@NotNull(message="Phonenumber is required")
	@Column(length=10)
	private long phoneNumber;
	
	@NotEmpty(message="Email is required")
	@Email
	@Column(unique=true)
	private String emailId;
	
	@NotEmpty(message="Password is required")
	@Size(min = 8, max = 15, message="Password should be between 8-15 characters")
	private String userPassword;
	
	@Enumerated(EnumType.STRING)
	private Role role;


	public User() {
		super();
		this.role = Role.USER;
	}

	public User(long userId, @NotEmpty(message = "Username is required") String userName,
			@NotNull(message = "Phonenumber is required") long phoneNumber,
			@NotEmpty(message = "Email is required") @Email String emailId,
			@NotEmpty(message = "Password is required") @Size(min = 8, max = 15, message = "Password should be between 8-15 characters") String userPassword
		) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.userPassword = userPassword;
		this.role = Role.USER;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", phoneNumber=" + phoneNumber + ", emailId="
				+ emailId + ", userPassword=" + userPassword + "]";
	}

	
	
	
	
}
