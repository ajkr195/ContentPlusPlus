package com.contentplusplus.springboot.service;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseType;

@Service
public interface AppCaseTypeService {
	
    void saveAppCaseType(AppCaseType appCaseType);

    AppCaseType findByappCaseTypenameIgnoreCase(String caseTypeName);
    
    AppCaseType updateappCaseType(AppCaseType appCaseType);
}
