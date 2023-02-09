package com.contentplusplus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.repository.AppCaseTypeDocumentTypeRepository;

@Service
public class AppCaseTypeDocumentTypeServiceImpl implements AppCaseTypeDocumentTypeService {

	@Autowired
	AppCaseTypeDocumentTypeRepository appCaseTypeDocumentTypeRepository;

	@Override
	public void saveAppCaseTypeDocType(AppCaseTypeDocumentType appCaseTypeDocumentType) {
		// TODO Auto-generated method stub
		appCaseTypeDocumentType.setCasetypedocumenttypename(appCaseTypeDocumentType.getCasetypedocumenttypename());

		appCaseTypeDocumentTypeRepository.save(appCaseTypeDocumentType);

	}

	@Override
	public AppCaseTypeDocumentType findByAppCaseTypeDocumentTypenameIgnoreCase(String caseTypeDocTypeName) {
		// TODO Auto-generated method stub
		return appCaseTypeDocumentTypeRepository.findByCasetypedocumenttypenameIgnoreCase(caseTypeDocTypeName);
	}

	@Override
	public AppCaseTypeDocumentType updateappCaseTypeDocumentType(AppCaseTypeDocumentType appCaseTypeDocumentType) {
		AppCaseTypeDocumentType entity = appCaseTypeDocumentTypeRepository.findById(appCaseTypeDocumentType.getId()).orElse(null);
		if (null != entity) {
			entity.setCasetypedocumenttypename(appCaseTypeDocumentType.getCasetypedocumenttypename());
		}
		return appCaseTypeDocumentTypeRepository.save(entity);
	}

}
