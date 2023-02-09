package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseHistory;

public interface AppCaseHistoryRepository extends JpaRepository<AppCaseHistory, Long> {
	
	List<AppCaseHistory> findByAppCase(AppCase appCase);
	
	List<AppCaseHistory> findByAppCaseOrderByIdDesc(AppCase appCase);
}
