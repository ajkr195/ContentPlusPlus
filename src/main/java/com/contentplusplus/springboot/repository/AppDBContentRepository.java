package com.contentplusplus.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppDBContent;

@Repository
public interface AppDBContentRepository extends JpaRepository<AppDBContent, Long> {

	Page<AppDBContent> findByFileNameContainingIgnoreCase(String keyword, PageRequest of);

	Page<AppDBContent> findByFileNameContainingIgnoreCase(String keyword, Pageable pageable);

}
