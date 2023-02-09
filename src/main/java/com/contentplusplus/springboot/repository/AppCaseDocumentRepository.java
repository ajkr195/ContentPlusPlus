package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppCaseDocument;

public interface AppCaseDocumentRepository extends JpaRepository<AppCaseDocument, Long> {
}
