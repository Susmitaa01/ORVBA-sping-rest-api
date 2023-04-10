package com.capgemini.vehiclebreakdown.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.vehiclebreakdown.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String username);
	
	User findByEmailId(String emailId);

	Optional<User> getUserByEmailId(String emailId);

	
}
