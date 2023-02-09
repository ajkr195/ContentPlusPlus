package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppCaseProperty;

@Repository
public interface AppCasePropertyRepository extends JpaRepository <AppCaseProperty, Long>{
	
	@Query(value="SELECT * FROM app_case_property WHERE caseid=:caseid", nativeQuery=true)
	List<AppCaseProperty> getCasePropertiesByCaseId(Long caseid);
	
}