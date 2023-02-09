package com.contentplusplus.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;

@Component
public class AppCaseTypeStepAddValidator implements Validator {

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return AppUser.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		AppCaseTypeStep appCaseTypeStep = (AppCaseTypeStep) o;

//		if (appCaseTypeStepRepository.findByAppCaseTypeAndCasetypestepnumberAndCasetypestepnameIgnoreCase(
//				appCaseTypeStep.getAppCaseType(), appCaseTypeStep.getCasetypestepnumber(),
//				appCaseTypeStep.getCasetypestepname()) != null) {
//			errors.rejectValue("casetypestepnumber", "duplicate.casetypestepnumber.error");
//			errors.rejectValue("casetypestepname", "duplicate.casetypestepname.error");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "casetypestepnumber", "not.empty.casetypestepnumber");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "casetypestepname", "not.empty.casetypestepname");
	}
}