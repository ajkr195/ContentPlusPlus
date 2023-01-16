package com.contentplusplus.springboot.service;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppDepartment;

@Service
public interface AppDepartmentService {
	
    void saveDepartment(AppDepartment appDepartment);

    AppDepartment findByDepartmentnameIgnoreCase(String deptName);
    
    AppDepartment updateDepartment(AppDepartment appDepartment);
}
