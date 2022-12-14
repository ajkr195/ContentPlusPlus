package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppContent;

@Repository
public interface AppContentRepository extends JpaRepository<AppContent, Long> {

}
