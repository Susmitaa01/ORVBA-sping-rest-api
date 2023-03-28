package com.capgemini.vehiclebreakdown.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.SearchMechanicListResponse;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.model.UserLoginRequest;
import com.capgemini.vehiclebreakdown.model.UserRegisterRequest;
import com.capgemini.vehiclebreakdown.model.UserRegisterResponse;
import com.capgemini.vehiclebreakdown.model.UserUpdateRequest;
import com.capgemini.vehiclebreakdown.model.UserUpdateResponse;
import com.capgemini.vehiclebreakdown.model.UserVerifyResponse;

public interface UserService {
	
	public ResponseEntity<UserRegisterResponse> userRegistration(UserRegisterRequest request);
	public ResponseEntity<UserLoginResponse> userLogin(UserLoginRequest request);
	public ResponseEntity<UserVerifyResponse> userVerify();
	
	public List<User> getAllUsers();
	public ResponseEntity<UserUpdateResponse> updateUser(UserUpdateRequest request);
	public Optional<User> getUserById(Long userId);
	public User getUserByEmailId(String emailId);
	public String sendRequest(AssistanceRequired assistanceRequired);
	
	public ResponseEntity<SearchMechanicListResponse> searchMechanicByLocation(String location) throws Exception;
	
	public String giveFeedback(Feedback feedback,long mechanicId,long userId);
	
}
