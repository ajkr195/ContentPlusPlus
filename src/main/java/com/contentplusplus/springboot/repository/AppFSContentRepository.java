package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppFSContent;

@Repository
public interface AppFSContentRepository extends JpaRepository<AppFSContent, Long> {

}
