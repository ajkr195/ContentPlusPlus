package com.contentplusplus.springboot.aws;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwsS3FileMetaRepository extends CrudRepository<AwsS3FileMeta, Integer> {
	
}