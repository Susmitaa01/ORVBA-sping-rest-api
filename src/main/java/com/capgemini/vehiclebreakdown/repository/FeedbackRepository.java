package com.capgemini.vehiclebreakdown.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.vehiclebreakdown.model.Feedback;
import com.capgemini.vehiclebreakdown.model.Mechanic;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
	public boolean findByUserId(long userId);
	public List<Feedback> findByMechanic(Mechanic m);
	public Feedback findByUserIdAndMechanic(long userId, Mechanic m);
	@Query("SELECT SUM(feed.ratings) from Feedback feed where feed.mechanic.mechanicId =:mechanicId")
	public Long sumOfRatings(@Param("mechanicId") long mechanicId);
	    
	@Query("SELECT COUNT(feed.ratings) from Feedback feed where feed.mechanic.mechanicId =:mechanicId")
	public Long countOfRatings(@Param("mechanicId") long mechanicId);
}
