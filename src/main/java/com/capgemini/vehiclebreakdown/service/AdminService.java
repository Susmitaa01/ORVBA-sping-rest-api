package com.capgemini.vehiclebreakdown.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.model.Admin;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.User;

public interface AdminService {

	public void initAdminUser();
	public List<User> getAllUsers();
	public Admin updateAdmin(Admin admin);
	public Optional<Admin> getAdminByUsername(String username);
	public List<Feedback> viewFeedback();
	public List<Mechanic> getAllMechanics();
	public ResponseEntity<String> allowOrBlockMechanic(long mechanicId) throws MechanicNotFoundException;
	
}
