package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseComment;

public interface AppCaseCommentRepository extends JpaRepository<AppCaseComment, Long> {

	List<AppCaseComment> findByAppCaseOrderByIdDesc(AppCase appCase);
}
