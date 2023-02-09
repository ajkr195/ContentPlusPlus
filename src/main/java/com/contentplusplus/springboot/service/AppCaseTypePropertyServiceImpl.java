package com.contentplusplus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeProperty;
import com.contentplusplus.springboot.repository.AppCaseTypePropertyRepository;

@Service
public class AppCaseTypePropertyServiceImpl implements AppCaseTypePropertyService {
	
	@Autowired
	AppCaseTypePropertyRepository appCaseTypePropertyRepository;

	@Override
	public void saveAppCaseType(AppCaseTypeProperty appCaseTypeProperty) {
		appCaseTypeProperty.setCasetypepropertyname(appCaseTypeProperty.getCasetypepropertyname());
		appCaseTypeProperty.setCasetypepropertyvalue(appCaseTypeProperty.getCasetypepropertyvalue());
		appCaseTypeProperty.setCasetypepropertytype(appCaseTypeProperty.getCasetypepropertytype());
		appCaseTypeProperty.setCasetypepropertysize(appCaseTypeProperty.getCasetypepropertysize());
		appCaseTypeProperty.setCasetypepropertyrequired(appCaseTypeProperty.getCasetypepropertyrequired());
		appCaseTypeProperty.setCasetypepropertymin(appCaseTypeProperty.getCasetypepropertymin());
		appCaseTypeProperty.setCasetypepropertymax(appCaseTypeProperty.getCasetypepropertymax());
		appCaseTypeProperty.setCasetypepropertymaxlength(appCaseTypeProperty.getCasetypepropertymaxlength());
		appCaseTypeProperty.setAppCaseType(appCaseTypeProperty.getAppCaseType());
		appCaseTypePropertyRepository.save(appCaseTypeProperty);
		
	}

	@Override
	public AppCaseTypeProperty findByappCaseTypePropertynameIgnoreCase(String caseTypePropertyName) {
		return appCaseTypePropertyRepository.findByCasetypepropertynameContainingIgnoreCase(caseTypePropertyName);
	}

	@Override
	public AppCaseTypeProperty updateappCaseType(AppCaseTypeProperty appCaseTypeProperty) {
		AppCaseTypeProperty entity = appCaseTypePropertyRepository.findById(appCaseTypeProperty.getId()).orElse(null);
		if (null != entity) {
			entity.setCasetypepropertyname(appCaseTypeProperty.getCasetypepropertyname());
			entity.setCasetypepropertyvalue(appCaseTypeProperty.getCasetypepropertyvalue());
			entity.setCasetypepropertytype(appCaseTypeProperty.getCasetypepropertytype());
			entity.setCasetypepropertysize(appCaseTypeProperty.getCasetypepropertysize());
			entity.setCasetypepropertyrequired(appCaseTypeProperty.getCasetypepropertyrequired());
			entity.setCasetypepropertymin(appCaseTypeProperty.getCasetypepropertymin());
			entity.setCasetypepropertymax(appCaseTypeProperty.getCasetypepropertymax());
			entity.setCasetypepropertymaxlength(appCaseTypeProperty.getCasetypepropertymaxlength());
			entity.setAppCaseType(appCaseTypeProperty.getAppCaseType());
		}
		return appCaseTypePropertyRepository.save(entity);
	}

}
