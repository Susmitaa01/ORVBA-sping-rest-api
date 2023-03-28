package com.capgemini.vehiclebreakdown.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.service.FeedbackService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	FeedbackService feedbackService;
	
	@GetMapping("/all")
	public List<Feedback> getAllFeedback(){
		return feedbackService.getAllFeedback();
		
	}
}
