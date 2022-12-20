package com.contentplusplus.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppFSContent;

@Repository
public interface AppFSContentRepository extends JpaRepository<AppFSContent, Long> {
	
//	@Modifying
//	@Query("DELETE FROM app_fs_content f WHERE f.filename = ?1")
	void deleteByFilename (String filename);

}
