package com.contentplusplus.springboot.aws;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3UploadService {

	S3File saveS3File(String title, String description, MultipartFile file);

	byte[] downloadS3File(Long id);

	List<S3File> getAllS3Files();
}