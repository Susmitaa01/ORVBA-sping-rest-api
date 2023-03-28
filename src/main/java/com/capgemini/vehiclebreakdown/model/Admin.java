package com.capgemini.vehiclebreakdown.model;

import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Admin {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
	@NotEmpty(message="Username is required")
	private String username;
    
	@JsonIgnore
	@NotEmpty(message="Password is required")
	private String password;
    
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Admin() {
		super();
		this.role=Role.ADMIN;
	}

	public Admin(@NotEmpty(message = "Username is required") String username,
			@NotEmpty(message = "Password is required") String password) {
		super();
		this.username = username;
		this.password = password;
		this.role = role.ADMIN;
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}

	
	
	
}
