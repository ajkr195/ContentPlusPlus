package com.contentplusplus.springboot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.model.AppUserDocument;
import com.contentplusplus.springboot.payload.AppUserDocumentUploadResponse;
import com.contentplusplus.springboot.repository.AppUserDocumentRepository;
import com.contentplusplus.springboot.service.AppUserDocumentStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AppUserDocumentRestController {

	@Autowired
	private AppUserDocumentStorageService appUserDocumentStorageService;

	@Autowired
	AppUserDocumentRepository appUserDocumentRepository;

	@PostMapping("/uploadFile")
	public AppUserDocumentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		AppUserDocument dbFile = appUserDocumentStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(dbFile.getId().toString()).toUriString();

		return new AppUserDocumentUploadResponse(dbFile.getDbfileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<AppUserDocumentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream()
				// .peek(fileBeingProcessed -> log.info("Processing File (PEEK2): {} ",
				// fileBeingProcessed))
				.map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
		// Load file from database
		AppUserDocument appUserDocument = appUserDocumentStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appUserDocument.getDbfileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + appUserDocument.getDbfileName() + "\"")
				.body(new ByteArrayResource(appUserDocument.getDbfileData()));
	}

	@DeleteMapping("/deletedbfile/{id}")
	public ResponseEntity<HttpStatus> deletedbfile(@PathVariable("id") Long id) {
		try {
			appUserDocumentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
