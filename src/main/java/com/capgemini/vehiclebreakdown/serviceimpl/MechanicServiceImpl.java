package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.exception.BlockByAdminException;
import com.capgemini.vehiclebreakdown.exception.InvalidLoginException;
import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.MechanicLoginRequest;
import com.capgemini.vehiclebreakdown.model.MechanicLoginResponse;
import com.capgemini.vehiclebreakdown.model.MechanicRegisterRequest;
import com.capgemini.vehiclebreakdown.model.MechanicRegisterResponse;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.UserRegisterResponse;
import com.capgemini.vehiclebreakdown.repository.AssistanceRequiredRepository;
import com.capgemini.vehiclebreakdown.repository.FeedbackRepository;
import com.capgemini.vehiclebreakdown.repository.MechanicRepository;
import com.capgemini.vehiclebreakdown.service.MechanicService;
import com.capgemini.vehiclebreakdown.util.JwtUtil;

@Service
public class MechanicServiceImpl implements MechanicService {

	@Autowired
	private MechanicRepository mechanicRepository;
	
	@Autowired
	private AssistanceRequiredRepository assistanceRequiredRepository;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public ResponseEntity<MechanicRegisterResponse> mechanicRegistration(MechanicRegisterRequest request) {
		Mechanic mechanic;
		MechanicRegisterResponse response = new MechanicRegisterResponse();
		
		mechanic = mechanicRepository.findByMechanicName(request.getMechanicName()).orElse(null);
		if(mechanic != null) {
			response.setStatus(false);
			response.setMessage("User already exists!");
			response.setMechanic(null);
		}
		else {
			mechanic = new Mechanic();
			mechanic.setMechanicName(request.getMechanicName());
			mechanic.setMechanicPhoneNumber(request.getMechanicPhoneNumber());
			mechanic.setMechanicEmailId(request.getMechanicEmailId());
			mechanic.setMechanicLocation(request.getMechanicLocation());
			mechanic.setMechanicPassword(request.getMechanicPassword());
			mechanic= mechanicRepository.save(mechanic);
			response.setStatus(true);
			response.setMessage("Mechanic Registered Successfully!");
			response.setMechanic(mechanic);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


	@Override
	public ResponseEntity<MechanicLoginResponse> mechanicLogin(MechanicLoginRequest request) {
		Mechanic mechanic;
		MechanicLoginResponse response = new MechanicLoginResponse();
		mechanic = mechanicRepository.findByMechanicName(request.getUsername()).orElse(null);
		if(mechanic == null) {
			response.setStatus(false);
			response.setMessage("Mechanic Not Found");
			response.setToken(null);
		}
		else {
			if(mechanic.getMechanicPassword().equals(request.getPassword())) {
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("role", mechanic.getRole());
				String token = jwtUtil.generateToken(claims, mechanic.getMechanicName());
				response.setStatus(true);
				response.setMessage("Mechanics Logged In");
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
	public Mechanic updateMechanic(Mechanic mechanic) {
		return mechanicRepository.save(mechanic);
	}

	@Override
	public Mechanic getMechanicByMechanicEmailId(String mechanicEmailId) {
		return mechanicRepository.findByMechanicEmailId(mechanicEmailId);
	}

	@Override
	public Optional<Mechanic> getMechanicByMechanicId(Long mechanicId) {
		return mechanicRepository.findById(mechanicId);
	}

	@Override
	public List<AssistanceRequired> viewRequest(long mechanicId)
			throws InvalidLoginException, MechanicNotFoundException {
		Mechanic mechanic= mechanicRepository.getById(mechanicId);
		return assistanceRequiredRepository.findByMechanicId(mechanicId);
	}

	@Override
	public List<Feedback> viewFeedback(long mechanicId) throws InvalidLoginException {
		Mechanic mechanic=mechanicRepository.getById(mechanicId);
		return feedbackRepository.findByMechanic(mechanic);
	}




}
