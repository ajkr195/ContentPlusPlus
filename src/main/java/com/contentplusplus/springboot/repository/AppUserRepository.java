package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUseremail(String useremail);
	
	AppUser findByUseremailIgnoreCase(String useremail);
	
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
}
