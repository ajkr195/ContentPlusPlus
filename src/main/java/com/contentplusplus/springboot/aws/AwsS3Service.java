package com.contentplusplus.springboot.aws;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface AwsS3Service {
    public PutObjectResult upload(
            String path,
            String fileName,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream);

    public S3Object download(String path, String fileName);
}