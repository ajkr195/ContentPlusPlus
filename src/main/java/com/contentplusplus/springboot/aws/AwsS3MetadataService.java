package com.contentplusplus.springboot.aws;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;

public interface AwsS3MetadataService {

	public void upload(MultipartFile file) throws IOException;

	public S3Object download(int id);

	public List<AwsS3FileMeta> list();
}