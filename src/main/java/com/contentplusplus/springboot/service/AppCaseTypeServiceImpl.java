package com.contentplusplus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;

@Service
public class AppCaseTypeServiceImpl implements AppCaseTypeService {
	
	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Override
	public void saveAppCaseType(AppCaseType appCaseType) {
		appCaseType.setCasetypename(appCaseType.getCasetypename());
		appCaseTypeRepository.save(appCaseType);
		
	}

	@Override
	public AppCaseType findByappCaseTypenameIgnoreCase(String caseTypeName) {
		return appCaseTypeRepository.findByCasetypenameContainingIgnoreCase(caseTypeName);
	}

	@Override
	public AppCaseType updateappCaseType(AppCaseType appCaseType) {
		AppCaseType entity = appCaseTypeRepository.findById(appCaseType.getId()).orElse(null);
		if (null != entity) {
			entity.setCasetypename(appCaseType.getCasetypename());
			entity.setCasetypeuuid(entity.getCasetypeuuid());
			entity.setCasetypedescription(appCaseType.getCasetypedescription());
			entity.setCasetypesladuration(appCaseType.getCasetypesladuration());
			entity.setCasetypeslaunit(appCaseType.getCasetypeslaunit());
			entity.setCasetypeuuid(entity.getCasetypeuuid());
			entity.setAppDepartment(entity.getAppDepartment());
		}
		return appCaseTypeRepository.save(entity);
	}
	
}
