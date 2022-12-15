package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppDBContent;

@Repository
public interface AppDBContentRepository extends JpaRepository<AppDBContent, Long> {

}
