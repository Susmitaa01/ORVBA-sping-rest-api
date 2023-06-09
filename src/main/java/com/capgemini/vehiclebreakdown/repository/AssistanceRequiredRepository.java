package com.capgemini.vehiclebreakdown.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.vehiclebreakdown.model.AssistanceRequired;

@Repository
public interface AssistanceRequiredRepository extends JpaRepository<AssistanceRequired, Long>{
public List<AssistanceRequired> findByMechanicId(long mechanicId);
	
	public AssistanceRequired getByUserId(long userId);
	
	public AssistanceRequired getByMechanicId(long mechanicId);
	
	public AssistanceRequired findByUserIdAndMechanicId(long userId, long mechanicId);

}
