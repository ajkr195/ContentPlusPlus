package com.contentplusplus.springboot.aws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class S3UploadServiceImpl implements S3UploadService {

	private final S3FileStore fileStore;

	private final S3FileRepository repository;

	@Override
	public S3File saveS3File(String title, String description, MultipartFile file) {
		// check if the file is empty
		if (file.isEmpty()) {
			throw new IllegalStateException("Cannot upload empty file");
		}
		/*
		 * Check if the file is an image 
		 * if (!Arrays.asList(IMAGE_PNG.getMimeType(),
		 * IMAGE_BMP.getMimeType(), IMAGE_GIF.getMimeType(),
		 * IMAGE_JPEG.getMimeType()).contains(file.getContentType())) { throw new
		 * IllegalStateException("FIle uploaded is not an image"); }
		 */
		
		
		// get file metadata
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));
		// Save Image in S3 and then save Todo in the database
		String path = String.format("%s/%s", S3Bucket.S3_FILE.getBucketName(), UUID.randomUUID());
		String fileName = String.format("%s", file.getOriginalFilename());
		try {
			fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
		} catch (IOException e) {
			throw new IllegalStateException("Failed to upload file", e);
		}
		S3File s3file = S3File.builder().description(description).title(title).imagePath(path).imageFileName(fileName)
				.build();
		repository.save(s3file);
		return repository.findByTitle(s3file.getTitle());
	}

	@Override
	public byte[] downloadS3File(Long id) {
		S3File s3file = repository.findById(id).get();
		return fileStore.download(s3file.getImagePath(), s3file.getImageFileName());
	}

	@Override
	public List<S3File> getAllS3Files() {
		List<S3File> s3files = new ArrayList<>();
		repository.findAll().forEach(s3files::add);
		return s3files;
	}
}