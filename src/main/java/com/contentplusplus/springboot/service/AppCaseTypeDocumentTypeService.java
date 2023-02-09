package com.contentplusplus.springboot.service;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;

@Service
public interface AppCaseTypeDocumentTypeService {
	
    void saveAppCaseTypeDocType(AppCaseTypeDocumentType  appCaseTypeDocumentType);

    AppCaseTypeDocumentType findByAppCaseTypeDocumentTypenameIgnoreCase(String appCaseTypeDocumentTypename);
    
    AppCaseTypeDocumentType updateappCaseTypeDocumentType(AppCaseTypeDocumentType appCaseTypeDocumentType);
}
