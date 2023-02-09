package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppCaseComment;

public interface AppCaseCommentRepository extends JpaRepository<AppCaseComment, Long> {
}
