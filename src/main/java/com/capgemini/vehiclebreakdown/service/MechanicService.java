package com.capgemini.vehiclebreakdown.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

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
import com.capgemini.vehiclebreakdown.model.UserLoginRequest;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;

public interface MechanicService {

	public ResponseEntity<MechanicRegisterResponse> mechanicRegistration(MechanicRegisterRequest request);
	public ResponseEntity<MechanicLoginResponse> mechanicLogin(MechanicLoginRequest request);
	public Mechanic updateMechanic(Mechanic mechanic);
	public Mechanic getMechanicByMechanicEmailId(String mechanicEmailId);
	public Optional<Mechanic> getMechanicByMechanicId(Long mechanicId);
	public List<AssistanceRequired> viewRequest(long mechanicId) throws InvalidLoginException, MechanicNotFoundException, BlockByAdminException;
	public List<Feedback> viewFeedback(long mechanicId) throws BlockByAdminException, InvalidLoginException;
	
	
}
