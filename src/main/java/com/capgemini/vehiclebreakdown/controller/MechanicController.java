package com.capgemini.vehiclebreakdown.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.vehiclebreakdown.exception.BlockByAdminException;
import com.capgemini.vehiclebreakdown.exception.FeedbackNotFoundException;
import com.capgemini.vehiclebreakdown.exception.InvalidLoginException;
import com.capgemini.vehiclebreakdown.exception.MechanicNotFoundException;
import com.capgemini.vehiclebreakdown.exception.RequestNotFoundException;
import com.capgemini.vehiclebreakdown.model.AssistanceRequired;
import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.MechanicLogin;
import com.capgemini.vehiclebreakdown.model.MechanicLoginRequest;
import com.capgemini.vehiclebreakdown.model.MechanicLoginResponse;
import com.capgemini.vehiclebreakdown.model.MechanicRegisterRequest;
import com.capgemini.vehiclebreakdown.model.MechanicRegisterResponse;
import com.capgemini.vehiclebreakdown.model.UserLoginRequest;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.UserRegisterRequest;
import com.capgemini.vehiclebreakdown.model.UserRegisterResponse;
import com.capgemini.vehiclebreakdown.repository.MechanicRepository;
import com.capgemini.vehiclebreakdown.service.MechanicService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/mechanic")
public class MechanicController {
	@Autowired
	private MechanicService mechanicService;
	@Autowired
	private MechanicRepository mechanicRepository;

	@PostMapping("/register")
	public ResponseEntity<MechanicRegisterResponse> registerMechanic(@Valid @RequestBody MechanicRegisterRequest request) {
        
        return mechanicService.mechanicRegistration(request);   
    }
	
	@PostMapping("/login")
	public ResponseEntity<MechanicLoginResponse> mechanicLogin(@RequestBody MechanicLoginRequest request) throws Exception
	{
		return mechanicService.mechanicLogin(request);
		
	}
	
	
	@GetMapping("/viewRequest/{mechId}")
	public ResponseEntity<List<AssistanceRequired>> viewingRequest(@PathVariable("mechId") long mechanicId)
			throws RequestNotFoundException, InvalidLoginException, MechanicNotFoundException, BlockByAdminException {
		List<AssistanceRequired> requestList = mechanicService.viewRequest(mechanicId);
		if (requestList.isEmpty()) {
			throw new RequestNotFoundException("Request not found with id:" + mechanicId);
		}
		return new ResponseEntity<List<AssistanceRequired>>(requestList, HttpStatus.OK);
	}

	// all mechanic view GET
	@GetMapping("/bymechanicid/{id}")
	public ResponseEntity<Mechanic> getMechanicByMechanicId(@PathVariable(value = "id") Long mechanicId) {
		Mechanic mechanic = (mechanicService.getMechanicByMechanicId(mechanicId)).orElse(null);
		return ResponseEntity.ok().body(mechanic);
	}

	@PutMapping("/update/bymechanicid/{id}")
	public ResponseEntity<Mechanic> updateMechanicByMechanicId(@PathVariable(value = "id") Long mechanicId,
			@Valid @RequestBody Mechanic mechanicinfo) throws MechanicNotFoundException {
		Mechanic mechanic = mechanicService.getMechanicByMechanicId(mechanicId)
				.orElseThrow(() -> new MechanicNotFoundException("Mechanic not Found"));
		mechanic.setMechanicEmailId(mechanicinfo.getMechanicEmailId());
		mechanic.setMechanicPhoneNumber(mechanicinfo.getMechanicPhoneNumber());
		mechanic.setMechanicName(mechanicinfo.getMechanicName());
		mechanic.setMechanicPassword(mechanicinfo.getMechanicPassword());
		mechanic.setMechanicLocation(mechanicinfo.getMechanicLocation());
		Mechanic updatedMechanic = mechanicService.updateMechanic(mechanic);
		return ResponseEntity.ok(updatedMechanic);
	}

	@GetMapping("/bymechanicemail/{email}")
	public ResponseEntity<Mechanic> getMechanicByMechanicEmail(@PathVariable(value = "email") String mechanicEmailId) {
		Mechanic mechanic = mechanicService.getMechanicByMechanicEmailId(mechanicEmailId);
		return ResponseEntity.ok().body(mechanic);
	}


	@GetMapping("/viewFeedback/{mechId}")
	public ResponseEntity<List<Feedback>> viewFeedback(@Valid @PathVariable("mechId") long mechanicId)
			throws FeedbackNotFoundException, BlockByAdminException, InvalidLoginException {

		List<Feedback> viewFeedback = mechanicService.viewFeedback(mechanicId);
		if (viewFeedback.isEmpty()) {
			throw new FeedbackNotFoundException("Feedback not found for the given mechanic id");
		}
		return new ResponseEntity<List<Feedback>>(viewFeedback, HttpStatus.OK);
	}
	
}
