package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.exception.AdminNotFoundException;
import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.model.Admin;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.repository.AdminRepository;
import com.capgemini.vehiclebreakdown.repository.FeedbackRepository;
import com.capgemini.vehiclebreakdown.repository.MechanicRepository;
import com.capgemini.vehiclebreakdown.repository.UserRepository;
import com.capgemini.vehiclebreakdown.service.AdminService;
import com.capgemini.vehiclebreakdown.service.MechanicService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private MechanicRepository mechanicRepository;
	
	@Autowired
	private MechanicService mechanicService;

	@Override
	public void initAdminUser() {
		Admin admin = adminRepository.findByUsername("admin").orElse(null);
		if(admin == null) {
			admin = new Admin("admin","admin");
			adminRepository.save(admin);
		}
		
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Optional<Admin> getAdminByUsername(String username) {
		return adminRepository.findByUsername(username);
	}

	@Override
	public List<Feedback> viewFeedback() {
		return feedbackRepository.findAll();
	}

	@Override
	public List<Mechanic> getAllMechanics() {
		return mechanicRepository.findAll();
	}

	@Override
	public ResponseEntity<String> allowOrBlockMechanic(long mechanicId) throws MechanicNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
