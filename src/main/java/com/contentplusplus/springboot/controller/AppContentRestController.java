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

import com.contentplusplus.springboot.model.AppContent;
import com.contentplusplus.springboot.payload.AppContentUploadResponse;
import com.contentplusplus.springboot.repository.AppContentRepository;
import com.contentplusplus.springboot.service.AppContentStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class AppContentRestController {

	@Autowired
	private AppContentStorageService appContentStorageService;

	@Autowired
	AppContentRepository appContentRepository;

	@PostMapping("/uploadFile")
	public AppContentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		AppContent appFile = appContentStorageService.storeFileInDB(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(appFile.getId().toString()).toUriString();
		log.info("Uploading file: {}", appFile.getFileName());
		appContentStorageService.storeFileInFileSystemStorage(file);
		return new AppContentUploadResponse(appFile.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<AppContentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

		return Arrays.asList(files).stream()
				// .peek(fileBeingProcessed -> log.info("Processing File : {} ",
				// fileBeingProcessed.getName()))
				.map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
		// Load file from database
		AppContent appContent = appContentStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appContent.getFileName() + "\"")
				.body(new ByteArrayResource(appContent.getFileData()));
	}

	@GetMapping("/viewFile/{fileId}")
	public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
		// Load file from database
		AppContent appContent = appContentStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + appContent.getFileName() + "\"")
				.body(new ByteArrayResource(appContent.getFileData()));
	}

	@DeleteMapping("/deletedbfile/{id}")
	public ResponseEntity<HttpStatus> deletedbfile(@PathVariable("id") Long id) {
		try {
			appContentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletealldbfiles")
	public ResponseEntity<HttpStatus> deleteAlldbfiles() {
		try {
			appContentRepository.deleteAll();
			appContentStorageService.deleteAllFromFileSystemStorage();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
