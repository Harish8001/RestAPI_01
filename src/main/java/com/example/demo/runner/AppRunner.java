package com.example.demo.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Reports;
import com.example.demo.repo.EligibilityRepo;

@Component
public class AppRunner implements ApplicationRunner {
	@Autowired
	private EligibilityRepo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		Reports s1 = new Reports();
		s1.setElgId((long) 1);
		s1.setName("Harish");
		s1.setPlanName("monthly");
		s1.setPlanStatus("Active");
		s1.setMobile("1234567890");
		s1.setEmail("harish@gmail.com");
		repo.save(s1);
		Reports s2 = new Reports();
		s2.setElgId((long) 2);
		s2.setName("Rakshitha");
		s2.setPlanName("Yearly");
		s2.setPlanStatus("Active");
		s2.setMobile("1234567890");
		s2.setEmail("Rakshi@gmail.com");
		repo.save(s2);
		Reports s3 = new Reports();
		s3.setElgId((long) 3);
		s3.setName("Yash");
		s3.setPlanName("Yearly");
		s3.setPlanStatus("Active");
		s3.setMobile("1234567890");
		s3.setEmail("yash@gmail.com");
		repo.save(s3);
		Reports s4 = new Reports();
		s4.setElgId((long) 4);
		s4.setName("Googly");
		s4.setPlanName("monthly");
		s4.setPlanStatus("Active");
		s4.setMobile("1234567890");
		s4.setEmail("googly@gmail.com");
		repo.save(s4);

	}

}
