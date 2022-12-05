package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contentplusplus.springboot.model.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByName(String name);
}
