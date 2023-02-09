package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseStatus;

public interface AppCaseRepository extends JpaRepository<AppCase, Long> {

	@Query(value = "SELECT casetypeid FROM app_case WHERE id=:id", nativeQuery = true)
	Long getCaseTypeId(Long id);

	List<AppCase> findByCasetitleIgnoreCaseContaining(String casetitle);

	List<AppCase> findByCasetitleIgnoreCase(String casetitle);

	List<AppCase> findByCaseuuidIgnoreCaseContaining(String caseuuid);

	List<AppCase> findByCaseuuidIgnoreCase(String caseuuid);

	List<AppCase> findByAssignedto(String assignedto);

	AppCase findByCaseuuid(String caseuuid);

	Long countByCasestatus(String caseStatus);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.lockedby =:useremailid where ac.caseuuid =:caseuuid")
	void setAppCaseLockedbyStatus(String useremailid, String caseuuid);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.lockedby =:useremailid where ac.caseuuid =:caseuuid")
	void unlockAppCase(String useremailid, String caseuuid);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.lockedby = null where ac.caseuuid =:caseuuid")
	void unlockAppCaseByUUID(String caseuuid);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.lockedby = null where ac.id =:caseid")
	void unlockAppCaseById(Long caseid);

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Query("UPDATE AppCase ac set ac.lockedby = null where ac.caseuuid =:caseuuid")
	void unlockCaseByCaseUUID(String caseuuid);

	Page<AppCase> findByCasestatus(String keyword, Pageable pageable);

	Page<AppCase> findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus appCaseStatus, String keyword,
			Pageable pageable);

	Page<AppCase> findByCasetitleContainingIgnoreCaseAndCasestatusContainingIgnoreCase(String keyword, String status,
			Pageable pageable);

	Page<AppCase> findByCasetitleContainingIgnoreCase(String keyword, Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.currentstepname =:currentstepname where ac.id =:caseid")
	void updateCurrentStep(String currentstepname, Long caseid);

	@Modifying
	@Transactional
	@Query("UPDATE AppCase ac set ac.casestatus =:casestatus where ac.id =:caseid")
	void updateCaseStatus(AppCaseStatus casestatus, Long caseid);

	@Query("SELECT COUNT(ac) FROM AppCase ac WHERE ac.casestatus=?1")
	Long getAllNewCasesCount(AppCaseStatus casestatus);

}
