package com.contentplusplus.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.service.AppUserService;

@Component
public class AppUserEditValidator implements Validator {
	
	@Autowired 
	AppUserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userfirstname", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userlastname", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "not.empty.multi.roles");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departments", "not.empty.multi.departments");

		
	}
}