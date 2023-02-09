package com.contentplusplus.springboot.service;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeProperty;

@Service
public interface AppCaseTypePropertyService {
	
    void saveAppCaseType(AppCaseTypeProperty appCaseTypeProperty);

    AppCaseTypeProperty findByappCaseTypePropertynameIgnoreCase(String caseTypePropertyName);
    
    AppCaseTypeProperty updateappCaseType(AppCaseTypeProperty appCaseTypeProperty);
}
