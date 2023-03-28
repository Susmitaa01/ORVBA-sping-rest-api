package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.repository.FeedbackRepository;
import com.capgemini.vehiclebreakdown.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Override
	public List<Feedback> getAllFeedback() {
		return feedbackRepository.findAll();
	}
}
