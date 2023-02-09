package com.contentplusplus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;

@Service
public class AppCaseTypeStepServiceImpl implements AppCaseTypeStepService {

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;

	@Override
	public void saveAppCaseType(AppCaseTypeStep appCaseTypeStep) {
		// TODO Auto-generated method stub
		appCaseTypeStep.setCasetypestepnumber(appCaseTypeStep.getCasetypestepnumber());
		appCaseTypeStep.setCasetypestepname(appCaseTypeStep.getCasetypestepname());
		appCaseTypeStep.setAppCaseType(appCaseTypeStep.getAppCaseType());

		appCaseTypeStepRepository.save(appCaseTypeStep);

	}

	@Override
	public AppCaseTypeStep findByappCaseTypeStepnameIgnoreCase(String caseTypeStepName) {
		// TODO Auto-generated method stub
		return appCaseTypeStepRepository.findByCasetypestepnameIgnoreCase(caseTypeStepName);
	}

	@Override
	public AppCaseTypeStep updateappCaseTypeStep(AppCaseTypeStep appCaseTypeStep) {
		AppCaseTypeStep entity = appCaseTypeStepRepository.findById(appCaseTypeStep.getId()).orElse(null);
		if (null != entity) {
			entity.setCasetypestepnumber(appCaseTypeStep.getCasetypestepnumber());
			entity.setCasetypestepname(appCaseTypeStep.getCasetypestepname());
			entity.setAppCaseType(appCaseTypeStep.getAppCaseType());
		}
		return appCaseTypeStepRepository.save(entity);
	}

}
