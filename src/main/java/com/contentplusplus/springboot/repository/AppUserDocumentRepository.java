package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppUserDocument;

@Repository
public interface AppUserDocumentRepository extends JpaRepository<AppUserDocument, Long> {

}
