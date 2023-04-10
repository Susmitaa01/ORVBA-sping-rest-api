package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.model.Admin;
import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.Role;
import com.capgemini.vehiclebreakdown.model.SearchMechanicListResponse;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.model.UserLoginRequest;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.UserRegisterRequest;
import com.capgemini.vehiclebreakdown.model.UserRegisterResponse;
import com.capgemini.vehiclebreakdown.model.UserRequestRequest;
import com.capgemini.vehiclebreakdown.model.UserRequestResponse;
import com.capgemini.vehiclebreakdown.model.UserUpdateRequest;
import com.capgemini.vehiclebreakdown.model.UserUpdateResponse;
import com.capgemini.vehiclebreakdown.model.UserVerifyResponse;
import com.capgemini.vehiclebreakdown.repository.AdminRepository;
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
	
	private AdminRepository adminRepository;
	
	@Autowired
	private AssistanceRequiredRepository assistanceRequiredRepository;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	//User Registration
	@Override
	public ResponseEntity<UserRegisterResponse> userRegistration(UserRegisterRequest request) {
		User user;
		UserRegisterResponse response = new UserRegisterResponse();
		user = userRepository.findByUserName(request.getUsername()).orElse(null);
		//Check if user already exist
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
			//save new user in database
			user = userRepository.save(user);
			//setting properties according to whether the user successfully registered
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
		//search for a user with the provided username
		user = userRepository.findByUserName(request.getUsername()).orElse(null);
		if(user == null) {
			response.setStatus(false);
			response.setMessage("User Not Found");
			response.setToken(null); //return null token
		}
		else {
			//check whether the provided password matches the user's stored password
			if(user.getUserPassword().equals(request.getPassword())) {
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("role", user.getRole());
				String token = jwtUtil.generateToken(claims, user.getUserName());
				response.setStatus(true);
				response.setMessage("User Logged In");
				response.setToken(token);//return generated token
			}
			else {
				response.setStatus(false);
				response.setMessage("Wrong Password");
				response.setToken(null);//return null token
			}
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//search mechanics by location
	@Override
	public ResponseEntity<SearchMechanicListResponse> searchMechanicByLocation(String location) throws Exception {
		//retrieve mechanic list	
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

	//method to verify users token to access protected resources in the application
	@Override
	public ResponseEntity<UserVerifyResponse> userVerify() {
		
		UserVerifyResponse response = new UserVerifyResponse();
		//retrive Authentication obj and extract user obj associated with it
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("verifying user");
		System.out.println(auth.getPrincipal());
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		//look up the user from user repo using user username
		User myUser = userRepository.findByUserName(user.getUsername()).orElse(null);
		//if user not found
		if(myUser == null) {
			response.setStatus(false);
			response.setMessage("User Does not exist");
			response.setUser(null);
		}
		else {
			response.setStatus(true);
			response.setMessage("token verified");
			response.setUser(myUser);//return user information
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
	public ResponseEntity<UserRequestResponse> sendRequest(UserRequestRequest request) {
		UserRequestResponse response = new UserRequestResponse();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
	    User myUser = userRepository.findByUserName(user.getUsername()).orElse(null);
	    if(myUser == null) {
	        response.setStatus(false);
	        response.setMessage("User Does not exist");
	        return new ResponseEntity<>(response,HttpStatus.OK);
	    }

	    boolean mechanicinfo = mechanicRepository.existsById(request.getMechanicId());
	    Mechanic mechanic=mechanicRepository.getById(request.getMechanicId());
	    if(mechanicinfo==false) {
	        response.setStatus(false);
	        response.setMessage("Mechanic does not exists. Please register first");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    if(!(request.getLocation().equals(mechanic.getMechanicLocation()))) {
	        response.setStatus(false);
	        response.setMessage("selected Mechanic is not present in location specified by user");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    AssistanceRequired assistanceRequired = new AssistanceRequired();
	    assistanceRequired.setAssistanceType(request.getAssistantType());
	    assistanceRequired.setUserId(myUser.getUserId());
	    assistanceRequired.setLocation(request.getLocation());
	    assistanceRequired.setMechanicId(mechanic.getMechanicId());
	    assistanceRequired.setFeedback(null);
	    assistanceRequiredRepository.save(assistanceRequired);
	    response.setStatus(true);
	    response.setMessage("Service Requested Successfully");
	    return new ResponseEntity<>(response,HttpStatus.OK);
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
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> (new UsernameNotFoundException(username)));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
				getUserAuthorities());
	}

	@Override
	public UserDetails loadUserByUsernameAndRole(String username, String role) throws UsernameNotFoundException {
		if(role.equals(Role.ADMIN.toString())) {
		    Admin admin = adminRepository.findByUsername(username).orElseThrow(()->(new UsernameNotFoundException(username)));
		    return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), getUserAuthorities());
		}else if(role.equals(Role.MECHANIC.toString())) {
		    Mechanic mechanic = mechanicRepository.findByMechanicName(username).orElseThrow(()->(new UsernameNotFoundException(username)));
		    return new org.springframework.security.core.userdetails.User(mechanic.getMechanicName(), mechanic.getMechanicPassword(), getMechanicAuthorities());
		}else {
		    User user= userRepository.findByUserName(username).orElseThrow(()->(new UsernameNotFoundException(username)));
		    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), getUserAuthorities());
		}
	}

	private Set getUserAuthorities() {
	    System.out.println("[+] [JwtService] [getAuthorities]: inside getUserAuthorities");
	    Set authorities = new HashSet();
	    authorities.add(new SimpleGrantedAuthority("USER"));
	    System.out.println("[+] [JwtService] [getAuthorities]: getUserAuthorities() ended");
	    return authorities;
	}

	private Set getMechanicAuthorities() {
	    System.out.println("[+] [JwtService] [getAuthorities]: inside getMechanicAuthorities");
	    Set authorities = new HashSet();
	    authorities.add(new SimpleGrantedAuthority("MECHANIC"));
	    System.out.println("[+] [JwtService] [getAuthorities]: getMechanicAuthorities() ended");
	    return authorities;
	}

	private Set getAdminAuthorities() {
	    System.out.println("[+] [JwtService] [getAuthorities]: inside getAdminAuthorities");
	    Set authorities = new HashSet();
	    authorities.add(new SimpleGrantedAuthority("ADMIN"));
	    System.out.println("[+] [JwtService] [getAuthorities]: getAdminAuthorities() ended");
	    return authorities;
	}

	
	
	
}
