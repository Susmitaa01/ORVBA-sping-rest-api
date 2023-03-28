package com.capgemini.vehiclebreakdown.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.vehiclebreakdown.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailId(String emailId);
	Optional<User> getUserByEmailId(String emailId);
	Optional<User> findByUserName(String username);
}
