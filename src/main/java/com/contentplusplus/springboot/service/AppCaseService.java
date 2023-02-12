package com.contentplusplus.springboot.service;

import java.util.List;

import com.contentplusplus.springboot.model.AppCase;

public interface AppCaseService {

	AppCase findByCaseId(Long id);

	void save(AppCase appCase);

	List<AppCase> findAllCases();

	void updateCase(AppCase appCase);
	
	void approveCase(AppCase appCase);
	
	void rejectCase(AppCase appCase);
	
	void forwardCase(Long id);
	
	void backwardCase(Long id);
	
	AppCase saveAppCase(AppCase appCase);
	
	AppCase saveAppCase2(AppCase appCase);
	
	void unlockCaseByCaseUUID (String caseuuid);

	void clearAssignedTo(Long id);
	
	void clearLockedBy(Long id);


}
