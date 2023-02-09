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
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;

@RestController
public class AppCaseTypeStepRESTController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;

	@PostMapping(value = "/appCaseTypeStep/{appCaseTypeId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppCaseTypeStep> createAppCaseType(
			@PathVariable(value = "appCaseTypeId") String appCaseTypeId, @RequestBody AppCaseTypeStep postdata) {

		System.out.println("theRequest : :" + postdata);

		AppCaseTypeStep appCaseTypeStep = appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId))
				.map(caseType -> {
					postdata.setAppCaseType(caseType);

					System.out.println("caseType Name: "
							+ appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId)).get().getCasetypename());

					if (null == appCaseTypeStepRepository.findByAppCaseTypeAndCasetypestepnameIgnoreCase(
							appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId)).get(),
							postdata.getCasetypestepname())) {
						return appCaseTypeStepRepository.save(postdata);
					}

					return null;

				}).orElseThrow(() -> new ResourceNotFoundException("Not found for id = " + appCaseTypeId));

		return new ResponseEntity<>(appCaseTypeStep, HttpStatus.CREATED);
	}

	@GetMapping("/appCaseTypeStep/{appCaseTypeId}")
	public ResponseEntity<List<AppCaseTypeStep>> getAllAppCaseTypePropssByappCaseTypeId(
			@PathVariable(value = "appCaseTypeId") Long appCaseTypeId) throws ResourceNotFoundException {

		List<AppCaseTypeStep> casetypesteps = appCaseTypeStepRepository
				.findAllAppCaseTypeStepsByAppCaseType(appCaseTypeRepository.findById(appCaseTypeId).get());

		return new ResponseEntity<>(casetypesteps, HttpStatus.OK); 
	}

}
