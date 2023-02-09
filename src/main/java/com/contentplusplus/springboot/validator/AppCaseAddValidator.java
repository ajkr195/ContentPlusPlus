package com.contentplusplus.springboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppUser;

@Component
public class AppCaseAddValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "casetitle", "not.empty.casetitle");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "appCaseType", "not.empty.appCaseCaseType");
		

	}
}