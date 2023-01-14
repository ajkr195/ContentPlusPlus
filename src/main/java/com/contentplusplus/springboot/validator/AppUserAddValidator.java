package com.contentplusplus.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.service.AppUserService;

@Component
public class AppUserAddValidator implements Validator {
	
	@Autowired 
	AppUserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		AppUser user = (AppUser) o;
		
		if (userService.findByUseremailIgnoreCase(user.getUseremail().trim()) != null) {
			errors.rejectValue("useremail", "duplicate.useremail.error");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userfirstname", "not.empty.userfirstname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userlastname", "not.empty.userlastname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "useremail", "not.empty.useremail");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userpassword", "not.empty.password");
		

		if (!user.getPasswordConfirm().equals(user.getUserpassword())) {
			errors.rejectValue("passwordConfirm", "password.passwordConfirm.mismatch");
		}
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "NotEmpty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "not.empty.multi.roles");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departments", "not.empty.multi.departments");

		
	}
}