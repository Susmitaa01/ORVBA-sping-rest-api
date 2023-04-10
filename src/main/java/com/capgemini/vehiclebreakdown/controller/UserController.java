package com.capgemini.vehiclebreakdown.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.exception.UserNotFoundException;
import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.AuthRequest;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
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
import com.capgemini.vehiclebreakdown.repository.UserRepository;
import com.capgemini.vehiclebreakdown.service.UserService;
import com.capgemini.vehiclebreakdown.util.JwtUtil;

import io.swagger.annotations.ApiImplicitParam;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
		
	private static final String String = null;

	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/register")
	public ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        
        return userService.userRegistration(request);   
    }
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest request) throws Exception
	{
		return userService.userLogin(request);
		
	}
	
	@GetMapping("/verify")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserVerifyResponse> tokenVerify()
	{
		return userService.userVerify();
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserUpdateResponse> updateUser(@Valid @RequestBody UserUpdateRequest request) throws UserNotFoundException
	{
		System.out.println("inside");
		return userService.updateUser(request);
	}
	
	@GetMapping("/searchMechanic/{loca}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<SearchMechanicListResponse> searchMechanic(@PathVariable(value="loca") String location) throws Exception {
		 return userService.searchMechanicByLocation(location);
	}
	
	
	@GetMapping("/all")
	public List<User> getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	@GetMapping("/byid/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId) throws UserNotFoundException
	{
			User user = userService.getUserById(userId).orElseThrow(()-> new UserNotFoundException("User not Found"));
			return ResponseEntity.ok().body(user);
		
	}
	
	@PostMapping("/addRequest")
	public ResponseEntity<UserRequestResponse> addRequest(@Valid @RequestBody UserRequestRequest userRequestRequest) {
	    return userService.sendRequest(userRequestRequest);
	}
	
	@GetMapping("/byemail/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable(value="email") String emailId)
	{
		User user = userService.getUserByEmailId(emailId);
		return ResponseEntity.ok().body(user);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}
	
	@PostMapping("/giveFeedback/{mechId}/{uId}")
	public ResponseEntity<String> giveNewFeedback(@Valid @PathVariable("mechId") long mechanicId,
			@PathVariable("uId") long userId, @RequestBody Feedback feedback) {

		String giveFeedback = userService.giveFeedback(feedback, mechanicId, userId);
		if (giveFeedback == null) {
			return new ResponseEntity<String>("Feedback not added", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(giveFeedback, HttpStatus.OK);
	}
	
}
