package com.contentplusplus.springboot.service;

import java.util.List;

import com.contentplusplus.springboot.model.AppUser;

public interface AppUserService {
    void saveUser(AppUser appuser);

    AppUser findByUseremail(String email);
    
    AppUser findByUseremailIgnoreCase(String email);

    List<AppUser> findAllUsers();
    
    List<AppUser> findAllActiveUsers();
    
    List<AppUser> findAllInActiveUsers();
}
