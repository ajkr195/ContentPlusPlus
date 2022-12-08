package com.contentplusplus.springboot.aws;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum S3Bucket {
    S3_FILE("spring-amazon-storage");
    private final String bucketName;
}