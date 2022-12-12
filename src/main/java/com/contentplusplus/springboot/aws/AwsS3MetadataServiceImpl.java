package com.contentplusplus.springboot.aws;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class AwsS3MetadataServiceImpl implements AwsS3MetadataService {

    @Autowired
    private AwsS3Service amazonS3Service;

    @Autowired
    private AwsS3FileMetaRepository fileMetaRepository;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public void upload(MultipartFile file) throws IOException {

        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());

        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), file.getInputStream());

        // Saving metadata to db
        fileMetaRepository.save(new AwsS3FileMeta(fileName, path, putObjectResult.getMetadata().getVersionId()));

    }

    @Override
    public S3Object download(int id) {
    	AwsS3FileMeta fileMeta = fileMetaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Exception"));
        return amazonS3Service.download(fileMeta.getAwsfilepath(),fileMeta.getAwsfilename());
    }

    @Override
    public List<AwsS3FileMeta> list() {
        List<AwsS3FileMeta> metas = new ArrayList<>();
        fileMetaRepository.findAll().forEach(metas::add);
        return metas;
    }
}