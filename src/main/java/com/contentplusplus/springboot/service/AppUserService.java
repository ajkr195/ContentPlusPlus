package com.contentplusplus.springboot.service;

import java.util.List;

import com.contentplusplus.springboot.entity.AppUser;

public interface AppUserService {
    void saveUser(AppUser appuser);

    AppUser findByEmail(String email);

    List<AppUser> findAllUsers();
}
