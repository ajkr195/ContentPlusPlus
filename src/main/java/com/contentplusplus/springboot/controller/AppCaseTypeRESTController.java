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

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.exception.ResourceNotFoundException;

@RestController
public class AppCaseTypeRESTController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@PostMapping(value = "/appDepartment/{appDepartMentId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppCaseType> createAppCaseType(
			@PathVariable(value = "appDepartMentId") String appDepartMentId, @RequestBody AppCaseType casetypename) {

		System.out.println("addressRequest : :" + casetypename);

		AppCaseType appCaseType = appDepartmentRepository.findById(Long.parseLong(appDepartMentId)).map(appuser -> {
			casetypename.setAppDepartment(appuser);

			System.out.println("AppDepartment Name: "
					+ appDepartmentRepository.findById(Long.parseLong(appDepartMentId)).get().getDepartmentheadname());

			if (null == appCaseTypeRepository.findByAppDepartmentAndCasetypenameIgnoreCase(
					appDepartmentRepository.findById(Long.parseLong(appDepartMentId)).get(),
					casetypename.getCasetypename())) {
				return appCaseTypeRepository.save(casetypename);
			}

			return null;

		}).orElseThrow(() -> new ResourceNotFoundException("Not found for deptId = " + appDepartMentId));

		return new ResponseEntity<>(appCaseType, HttpStatus.CREATED);
	}

	@GetMapping("/appCaseType/{appDepartmentId}/appCaseTypes")
	public ResponseEntity<List<AppCaseType>> getAllAppCaseTypesByDepartmentId(
			@PathVariable(value = "appDepartmentId") Long appDepartmentId) throws ResourceNotFoundException {
//	    if (!appCaseTypeRepository.existsById(appDepartmentId)) {
//	      throw new ResourceNotFoundException("Not CaseType records found for Department with id = " + appDepartmentId);
//	    }
		List<AppCaseType> addresses = appCaseTypeRepository
				.findAllAppCaseTypeByAppDepartment(appDepartmentRepository.findById(appDepartmentId).get());

		return new ResponseEntity<>(addresses, HttpStatus.OK); // findAllAppCaseTypeByAppDepartment
																// //findAllByAppDepartmentOrderByIdAsc
	}

}
