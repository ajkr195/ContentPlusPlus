package com.contentplusplus.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;

@Repository
public interface AppWorkflowDocumentRepository extends JpaRepository<AppWorkflowDocument, Long> {
	
	@Modifying
	@Transactional
	@Query("UPDATE AppWorkflowDocument awfd set awfd.fileData = ?1 where awfd.id = ?2")
	AppWorkflowDocument updateWFDocumentContent(byte[] fileData, Long id);
	
	Page<AppWorkflowDocument> findByWorkflowstatus(AppWorkFlowDocumentStatus statusDraft, PageRequest pageRequest);

}
