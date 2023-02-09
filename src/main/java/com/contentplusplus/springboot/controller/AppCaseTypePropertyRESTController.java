package com.contentplusplus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.contentplusplus.springboot.exception.ResourceNotFoundException;
import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppCaseTypeProperty;
import com.contentplusplus.springboot.repository.AppCaseTypePropertyRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;

@RestController
public class AppCaseTypePropertyRESTController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppCaseTypePropertyRepository appCaseTypePropertyRepository;

	@PostMapping(value = "/appCaseTypeProperty/{appCaseTypeId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppCaseTypeProperty> createAppCaseType(
			@PathVariable(value = "appCaseTypeId") String appCaseTypeId, @RequestBody AppCaseTypeProperty postdata) {

		System.out.println("addressRequest : :" + postdata);

		AppCaseTypeProperty appCaseTypeProperty = appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId))
				.map(caseType -> {
					postdata.setAppCaseType(caseType);

					System.out.println("caseType Name: "
							+ appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId)).get().getCasetypename());

					if (null == appCaseTypePropertyRepository.findByAppCaseTypeAndCasetypepropertynameIgnoreCase(
							appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId)).get(),
							postdata.getCasetypepropertyname())) {
						return appCaseTypePropertyRepository.save(postdata);
					}

					return null;

				}).orElseThrow(() -> new ResourceNotFoundException("Not found for deptId = " + appCaseTypeId));

		return new ResponseEntity<>(appCaseTypeProperty, HttpStatus.CREATED);
	}

	@GetMapping("/appCaseTypeProperty/{appCaseTypeId}/appCaseTypeProperty")
	public ResponseEntity<List<AppCaseTypeProperty>> getAllAppCaseTypePropssByappCaseTypeId(
			@PathVariable(value = "appCaseTypeId") Long appCaseTypeId) throws ResourceNotFoundException {
//	    if (!appCaseTypeRepository.existsById(appDepartmentId)) {
//	      throw new ResourceNotFoundException("Not CaseType records found for Department with id = " + appDepartmentId);
//	    }
		List<AppCaseTypeProperty> casetypeprops = appCaseTypePropertyRepository
				.findAllAppCaseTypePropertiesByAppCaseType(appCaseTypeRepository.findById(appCaseTypeId).get());

		return new ResponseEntity<>(casetypeprops, HttpStatus.OK); // findAllAppCaseTypeByAppDepartment
																	// //findAllByAppDepartmentOrderByIdAsc
																	// //findByFewFields
	}

}
