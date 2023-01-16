package com.contentplusplus.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.service.AppDepartmentService;

@Component
public class AppDepartmentAddValidator implements Validator {

	@Autowired
	AppDepartmentService appDepartmentService;

	@Override
	public boolean supports(Class<?> aClass) {
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		AppDepartment appDepartment = (AppDepartment) o;

		if (appDepartmentService.findByDepartmentnameIgnoreCase(appDepartment.getDepartmentname().trim()) != null) {
			errors.rejectValue("departmentname", "duplicate.departmentname.error");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentname", "not.empty.departmentname");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentheadname", "not.empty.departmentheadname");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departmentheademail", "not.empty.departmentheademail");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "appUser", "not.empty.appUser");

	}
}