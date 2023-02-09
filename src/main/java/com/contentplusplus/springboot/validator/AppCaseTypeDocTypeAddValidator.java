package com.contentplusplus.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppCaseTypeDocumentTypeRepository;

@Component
public class AppCaseTypeDocTypeAddValidator implements Validator {

	@Autowired
	AppCaseTypeDocumentTypeRepository appCaseTypeDocumentTypeRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return AppCaseTypeDocumentType.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		AppCaseTypeDocumentType appCaseTypeStep = (AppCaseTypeDocumentType) o;  

//		if (appCaseTypeStepRepository.findByAppCaseTypeAndCasetypestepnumberAndCasetypestepnameIgnoreCase(
//				appCaseTypeStep.getAppCaseType(), appCaseTypeStep.getCasetypestepnumber(),
//				appCaseTypeStep.getCasetypestepname()) != null) {
//			errors.rejectValue("casetypestepnumber", "duplicate.casetypestepnumber.error");
//			errors.rejectValue("casetypestepname", "duplicate.casetypestepname.error");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "casetypedocumenttypename", "not.empty.casetypedocumenttypename");
	}
}