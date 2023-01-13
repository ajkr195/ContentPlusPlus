package com.contentplusplus.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.contentplusplus.springboot.model.AppUser;

@Service
public interface AppUserService {
    void saveUser(AppUser appuser);

    AppUser findByUseremail(String email);
    
    AppUser findByUseremailIgnoreCase(String email);

    List<AppUser> findAllUsers();
    
    List<AppUser> findAllActiveUsers();
    
    List<AppUser> findAllInActiveUsers();

	AppUser updateUser(AppUser user);
}
