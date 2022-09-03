package com.codezmr.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.codezmr.entity.EligibilityDetails;
import com.codezmr.repository.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired
	private EligibilityDetailsRepo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(1);
		entity1.setName("Leaon");
		entity1.setEmail("Leaon@gmail.com");
		entity1.setMobile(9911223344L);
		entity1.setGender('M');
		entity1.setSsn(77821L);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		repo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(2);
		entity2.setName("Zenop");
		entity2.setEmail("zenop@outlook.com");
		entity2.setMobile(9981227644L);
		entity2.setGender('M');
		entity2.setSsn(37224L);
		entity2.setPlanName("FOOD");
		entity2.setPlanStatus("Denied");
		repo.save(entity2);

		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(3);
		entity3.setName("Reiva Roy");
		entity3.setEmail("rivakuni@gmail.com");
		entity3.setMobile(8931243341L);
		entity3.setGender('F');
		entity3.setSsn(79111L);
		entity3.setPlanName("FOOD");
		entity3.setPlanStatus("Approved");
		repo.save(entity3);
		
		EligibilityDetails entity4 = new EligibilityDetails();
		entity4.setEligId(4);
		entity4.setName("Humna Ruth");
		entity4.setEmail("huru@code.in");
		entity4.setMobile(7919923084L);
		entity4.setGender('M');
		entity4.setSsn(65233L);
		entity4.setPlanName("CCAP");
		entity4.setPlanStatus("Approved");
		repo.save(entity4);

	}

}
