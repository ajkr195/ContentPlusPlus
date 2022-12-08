package com.contentplusplus.springboot.aws;

import org.springframework.data.repository.CrudRepository;

public interface S3FileRepository extends CrudRepository<S3File, Long> {
	S3File findByTitle(String title);
}