package com.contentplusplus.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;

@Service
public class AppDepartmentServiceImpl implements AppDepartmentService{
	
	@Autowired 
	AppDepartmentRepository appDepartmentRepository;
	
	@Override
	public void saveDepartment(AppDepartment appDepartment) {
		appDepartment.setDepartmentname(appDepartment.getDepartmentname());
		appDepartment.setDepartmentheadname(appDepartment.getAppUser().getUserfirstname() + " " + appDepartment.getAppUser().getUserlastname());
		appDepartment.setDepartmentemaildistlist(appDepartment.getDepartmentemaildistlist());
		appDepartment.setAppUser(appDepartment.getAppUser());
		appDepartmentRepository.save(appDepartment);
	}

	
	@Override
	public AppDepartment findByDepartmentnameIgnoreCase(String deptName) {
		return appDepartmentRepository.findByDepartmentnameIgnoreCase(deptName);
	}

	@Override
    public AppDepartment updateDepartment(AppDepartment appDepartment) {
		AppDepartment entity = appDepartmentRepository.findById(appDepartment.getId()).orElse(null);
		if (null != entity) {
			entity.setDepartmentname(appDepartment.getDepartmentname());
			entity.setDepartmentheadname(appDepartment.getAppUser().getUserfirstname() + " " + appDepartment.getAppUser().getUserlastname());
			entity.setDepartmentemaildistlist(appDepartment.getDepartmentemaildistlist());
			entity.setAppUser(appDepartment.getAppUser());
			
		}
		return appDepartmentRepository.save(entity);
        
    }
	
}
