package com.contentplusplus.springboot.repository;

import java.util.List;
import java.util.TreeSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;

public interface AppCaseTypeStepRepository extends JpaRepository<AppCaseTypeStep, Long> {

	List<AppCaseTypeStep> findAllAppCaseTypeStepsByAppCaseType(AppCaseType appCaseType);

	AppCaseTypeStep findByAppCaseTypeAndCasetypestepnameIgnoreCase(AppCaseType appCaseType, String casetypestepname);

	AppCaseTypeStep findByAppCaseTypeAndCasetypestepnumberAndCasetypestepnameIgnoreCase(AppCaseType appCaseType,
			Integer casetypestepnumber, String casetypestepname);

	AppCaseTypeStep findByCasetypestepnameIgnoreCase(String caseTypeStepName);

	Page<AppCaseTypeStep> findByCasetypestepnameContainingIgnoreCase(String keyword, Pageable pageable);

	@Query("select acs.id from AppCaseTypeStep acs where acs.appCaseType =:appCaseType")
	TreeSet<Long> getStepIdsByCaseType(@Param("appCaseType") AppCaseType appCaseType);
	
	@Query("select acs.id from AppCaseTypeStep acs where acs.appCaseType =:appCaseType")
	List<Long> getTheStepIdsByCaseType(@Param("appCaseType") AppCaseType appCaseType);

}
