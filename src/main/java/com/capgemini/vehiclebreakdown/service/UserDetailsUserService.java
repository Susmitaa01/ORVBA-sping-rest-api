package com.capgemini.vehiclebreakdown.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.capgemini.vehiclebreakdown.repository.UserRepository;

public interface UserDetailsUserService {

	public UserDetails findUserByUsername(String username);
	
}
