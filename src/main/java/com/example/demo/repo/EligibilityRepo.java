package com.example.demo.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reports;

@Repository
public interface EligibilityRepo extends JpaRepository<Reports, Integer> {

	@Query("SELECT DISTINCT e.planName FROM Reports e")
	public List<String> findDistinctColumnName();

	@Query("SELECT DISTINCT e.planStatus FROM Reports e")
	public List<String> findDistinctStatus();
}
