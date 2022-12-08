package com.contentplusplus.springboot.aws;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/s3file")
@AllArgsConstructor
@CrossOrigin("*")

public class S3FileController {

	S3UploadService service;

	@GetMapping
	public ResponseEntity<List<S3File>> getS3Files() {
		return new ResponseEntity<>(service.getAllS3Files(), HttpStatus.OK);
	}

	@PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<S3File> saveS3File(@RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
		return new ResponseEntity<>(service.saveS3File(title, description, file), HttpStatus.OK);
	}

	@GetMapping(value = "{id}/image/download")
	public byte[] downloadS3FileImage(@PathVariable("id") Long id) {
		return service.downloadS3File(id);
	}

}