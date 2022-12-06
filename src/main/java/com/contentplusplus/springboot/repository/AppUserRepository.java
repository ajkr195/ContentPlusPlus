package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUseremail(String useremail);
	
	AppUser findByUseremailIgnoreCase(String useremail);
}
