package com.codezmr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codezmr.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Integer>{
	
	@Query("SELECT DISTINCT(planName) FROM EligibilityDetails")
	public List<String> findPlanNames();
	
	@Query("SELECT DISTINCT(planStatus) FROM EligibilityDetails")
	public List<String> findPlanStatuses();
	
	
}
