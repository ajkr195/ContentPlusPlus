package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppCaseTypeProperty;

public interface AppCaseTypePropertyRepository extends JpaRepository<AppCaseTypeProperty, Long> {

	AppCaseTypeProperty findByCasetypepropertynameContainingIgnoreCase(String caseTypePropertyName);

	Page<AppCaseTypeProperty> findByCasetypepropertynameContainingIgnoreCase(String keyword, Pageable pageable);

	List<AppCaseTypeProperty> findAllAppCaseTypePropertiesByAppCaseType(AppCaseType appCaseType);

	AppCaseTypeProperty findByAppCaseTypeAndCasetypepropertynameIgnoreCase(AppCaseType appCaseType, String casetypepropertyname);

	@Query(value="SELECT * FROM app_case_type_property WHERE casetypeid=:casetypeid", nativeQuery=true)
	List<AppCaseTypeProperty> getCaseTypePropertiesByCaseTypeId(Long casetypeid);

}
