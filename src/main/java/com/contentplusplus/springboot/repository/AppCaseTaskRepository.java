package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppCaseTask;

public interface AppCaseTaskRepository extends JpaRepository<AppCaseTask, Long> {

	List<AppCaseTask> findByAppCaseOrderByIdDesc(AppCaseTask appCase);

	@Query("UPDATE AppCaseTask act SET act.taskcompleted = :taskcompleted WHERE act.id = :id")
	@Modifying
	@Transactional
	public void updateCompletionStatus(Long id, boolean taskcompleted);
}
