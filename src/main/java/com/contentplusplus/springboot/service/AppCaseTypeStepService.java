package com.contentplusplus.springboot.service;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeStep;

@Service
public interface AppCaseTypeStepService {
	
    void saveAppCaseType(AppCaseTypeStep appCaseTypeStep);

    AppCaseTypeStep findByappCaseTypeStepnameIgnoreCase(String caseTypePropertyName);
    
    AppCaseTypeStep updateappCaseTypeStep(AppCaseTypeStep appCaseTypeStep);
}
