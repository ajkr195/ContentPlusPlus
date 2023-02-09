package com.contentplusplus.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;

public interface AppCaseTypeDocumentTypeRepository extends JpaRepository<AppCaseTypeDocumentType, Long> {

	AppCaseTypeDocumentType findByCasetypedocumenttypenameIgnoreCase(String caseTypeDocTypeName);

	Page<AppCaseTypeDocumentType> findByCasetypedocumenttypenameContainingIgnoreCase(String keyword, Pageable pageable);
}
