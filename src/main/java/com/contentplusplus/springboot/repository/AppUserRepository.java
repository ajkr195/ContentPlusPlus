package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseComment;
import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUseremail(String useremail);
	
	AppUser findByUseremailIgnoreCase(String useremail);
	
	Page<AppUser> findByUseremailContainingIgnoreCase(String keyword, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE AppUser au set au.userenabled = true where au.id = ?1")
	void setAppUserAsActiveById(Long id);

	@Modifying
	@Transactional
	@Query("UPDATE AppUser au set au.userenabled = false where au.id = ?1")
	void setAppUserAsInActiveById(Long id);
	
	List<AppUser> findByUserenabledTrue();

	List<AppUser> findByUserenabledFalse();
	
	@Query("select useremail, userfirstname, userlastname from AppUser where userenabled = true")
	List<AppUser> getActiveUsers();

	@Query("select useremail, userfirstname, userlastname from AppUser where userenabled = false")
	List<AppUser> getInActiveUsers();

	Page<AppUser> findByUserenabledAndUseremailContainingIgnoreCase(Boolean valueOf, String keyword, Pageable pageable);

	Page<AppUser> findByUserenabledTrueAndUseremailContainingIgnoreCase(String keyword,
			Pageable pageable);

	Page<AppUser> findByUserenabledFalseAndUseremailContainingIgnoreCase(String keyword,
			Pageable pageable);

	Page<AppUser> findByUserenabledFalse(Boolean valueOf, Pageable pageable);

	Page<AppUser> findByUserenabledTrue(Boolean valueOf, Pageable pageable);

	Page<AppUser> findByUserenabledTrue(Pageable pageable);

	Page<AppUser> findByUserenabledFalse(Pageable pageable);

	Page<AppUser> findByUseremailContainingIgnoreCaseAndUserenabledFalse(String keyword, Pageable pageable);
	
	List<AppUser> findByDepartmentsIn(List<AppDepartment> dept);

}
