package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.SearchMechanicListResponse;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.model.UserLoginRequest;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.UserRegisterRequest;
import com.capgemini.vehiclebreakdown.model.UserRegisterResponse;
import com.capgemini.vehiclebreakdown.model.UserUpdateRequest;
import com.capgemini.vehiclebreakdown.model.UserUpdateResponse;
import com.capgemini.vehiclebreakdown.model.UserVerifyResponse;
import com.capgemini.vehiclebreakdown.repository.AssistanceRequiredRepository;
import com.capgemini.vehiclebreakdown.repository.FeedbackRepository;
import com.capgemini.vehiclebreakdown.repository.MechanicRepository;
import com.capgemini.vehiclebreakdown.repository.UserRepository;
import com.capgemini.vehiclebreakdown.service.UserService;
import com.capgemini.vehiclebreakdown.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MechanicRepository mechanicRepository;
	
	@Autowired
	private AssistanceRequiredRepository assistanceRequiredRepository;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public ResponseEntity<UserRegisterResponse> userRegistration(UserRegisterRequest request) {
		User user;
		UserRegisterResponse response = new UserRegisterResponse();
		user = userRepository.findByUserName(request.getUsername()).orElse(null);
		if(user != null) {
			response.setStatus(false);
			response.setMessage("User already exists!");
			response.setUser(null);
		}
		else {
			user = new User();
			user.setUserName(request.getUsername());
			user.setPhoneNumber(request.getPhoneNumber());
			user.setEmailId(request.getEmailId());
			user.setUserPassword(request.getUserPassword());
			user = userRepository.save(user);
			response.setStatus(true);
			response.setMessage("User Registered Successfully!");
			response.setUser(user);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserLoginResponse> userLogin(UserLoginRequest request) {
		User user;
		UserLoginResponse response = new UserLoginResponse();
		user = userRepository.findByUserName(request.getUsername()).orElse(null);
		if(user == null) {
			response.setStatus(false);
			response.setMessage("User Not Found");
			response.setToken(null);
		}
		else {
			if(user.getUserPassword().equals(request.getPassword())) {
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("role", user.getRole());
				String token = jwtUtil.generateToken(claims, user.getUserName());
				response.setStatus(true);
				response.setMessage("User Logged In");
				response.setToken(token);
			}
			else {
				response.setStatus(false);
				response.setMessage("Wrong Password");
				response.setToken(null);
			}
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<SearchMechanicListResponse> searchMechanicByLocation(String location) throws Exception {
			
		List<Mechanic> mechanicList = mechanicRepository.findByMechanicLocation(location);
			if (mechanicList.isEmpty())
			{
				throw new MechanicNotFoundException("Mechanic does not exist at " + location + " location");
			}
			SearchMechanicListResponse response = new SearchMechanicListResponse();
			response.setStatus(true);
			response.setMessage("mechanic list fetched");
			response.setMechanic_list(mechanicList);
			return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<UserVerifyResponse> userVerify() {
		
		UserVerifyResponse response = new UserVerifyResponse();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("verifying user");
		System.out.println(auth.getPrincipal());
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		User myUser = userRepository.findByUserName(user.getUsername()).orElse(null);
		if(myUser == null) {
			response.setStatus(false);
			response.setMessage("User Does not exist");
			response.setUser(null);
		}
		else {
			response.setStatus(true);
			response.setMessage("token verified");
			response.setUser(myUser);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	

	@Override
	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User getUserByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	@Override
	public String sendRequest(AssistanceRequired assistanceRequired) {
		boolean userinfo = userRepository.existsById(assistanceRequired.getUserId());
		User user=userRepository.getById(assistanceRequired.getUserId());
		
		if(userinfo==false)
        {
        	return "User does not exists. Please register first";
        }
		boolean mechanicinfo = mechanicRepository.existsById(assistanceRequired.getMechanicId());
		Mechanic mechanic=mechanicRepository.getById(assistanceRequired.getMechanicId());
		if(mechanicinfo==false)
        {
        	return "Mechanic does not exists. Please register first";
        }
		if(!(assistanceRequired.getLocation().equals(mechanic.getMechanicLocation())))
		{
			return "selected Mechanic is not present in location specified by user";
		}
		
		assistanceRequiredRepository.save(assistanceRequired);
		return "Service Requested Successfully";
	}
	
	@Override
	public ResponseEntity<UserUpdateResponse> updateUser(UserUpdateRequest request) {
		User user;
		UserUpdateResponse response = new UserUpdateResponse();
		user = userRepository.findByUserName(request.getUsername()).orElse(null);
		if(user == null) {
			response.setStatus(false);
			response.setMessage("User does not exist!");
			//response.setUser(null);
		}
		else {
			user.setUserName(request.getUsername());
			user.setPhoneNumber(request.getPhoneNumber());
			user.setEmailId(request.getEmailId());
			user = userRepository.save(user);
			response.setStatus(true);
			response.setMessage("User Updated!");
			//response.setUser(user);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


	
	@Override
	public String giveFeedback(Feedback feedback, long mechanicId, long userId) {
		Mechanic mechanic=mechanicRepository.getById(mechanicId);
		User user=userRepository.getById(userId);
		
		AssistanceRequired ar = assistanceRequiredRepository.findByUserIdAndMechanicId(userId,mechanicId);
		
		if(ar== null)
		{
			return "Service for given user and mechanic doesnt exist";
		}
		
		Feedback feed= feedbackRepository.findByUserIdAndMechanic(userId,mechanic);
		if(feed!=null) {
			return "Feedback Already Exists"; 
		}
		
		Feedback feedback1=new Feedback();
		feedback1.setUserId(userId);
		feedback1.setFeedbackMessage(feedback.getFeedbackMessage());
		feedback1.setRatings(feedback.getRatings());
		feedback1.setMechanic(mechanic);
		feedback1.setAssiatnceRequired(ar);
		feedbackRepository.save(feedback1);
		return "Feedback Added";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUserName(username).orElseThrow(()->(new UsernameNotFoundException(username)));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), new ArrayList());
	}

	


	

}
