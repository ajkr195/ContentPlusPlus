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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.payload.AppContentUploadResponse;
import com.contentplusplus.springboot.repository.AppWorkflowDocumentRepository;
import com.contentplusplus.springboot.service.AppWFContentStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class AppWFContentRestController {

	@Autowired
	private AppWFContentStorageService appWFContentStorageService;

	@Autowired
	AppWorkflowDocumentRepository appWorkflowDocumentRepository;

	@PostMapping("/uploadWFFile")
	public AppContentUploadResponse uploadWFFile(@RequestParam("file") MultipartFile file) {
		
		AppWorkflowDocument appFile = appWFContentStorageService.storeWFFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(appFile.getId().toString()).toUriString();
		log.info("Uploading file: {}", appFile.getFileName());
		
		return new AppContentUploadResponse(appFile.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	@PostMapping("/uploadMultipleWFFiles")
	public List<AppContentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

		return Arrays.asList(files).stream()
				// .peek(fileBeingProcessed -> log.info("Processing File : {} ",
				// fileBeingProcessed.getName()))
				.map(file -> uploadWFFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadWFFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
		// Load file from database
		AppWorkflowDocument appContent = appWFContentStorageService.getWFFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appContent.getFileName() + "\"")
				.body(new ByteArrayResource(appContent.getFileData()));
	}

	@GetMapping("/viewWFFile/{fileId}")
	public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
		// Load file from database
		AppWorkflowDocument appContent = appWFContentStorageService.getWFFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + appContent.getFileName() + "\"")
				.body(new ByteArrayResource(appContent.getFileData()));
	}

	@DeleteMapping("/deleteWFfile/{id}")
	public ResponseEntity<HttpStatus> deletedbfile(@PathVariable("id") Long id) {
		try {
			appWorkflowDocumentRepository.deleteById(id);
			log.info("Deleted Workflow file with Id: {}", id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/updateAppWFDocument/{id}")
	public AppContentUploadResponse updateWFFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
		
		AppWorkflowDocument appFile = appWFContentStorageService.updateWFFile(file, id);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(appFile.getId().toString()).toUriString();
		log.info("Uploading file: {}", appFile.getFileName());
		
		return new AppContentUploadResponse(appFile.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}
	
	
	@PutMapping("/nextStepDocWorkflow/{id}")
	public ResponseEntity<AppWorkflowDocument> forwardWFlow(@PathVariable Long id) {

		AppWorkflowDocument appWorkflowDocument = appWorkflowDocumentRepository.findById(id).get();

		System.out.println("Moving forward this Workflow Doc Id: " + id);

		if (appWorkflowDocument == null)
			return ResponseEntity.notFound().build();

		try {
			appWorkflowDocument.setWorkflowstatus(
					AppWorkFlowDocumentStatus.values()[appWorkflowDocument.getWorkflowstatus().ordinal() + 1]);
		} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			System.out.println("Reached Last step already...");
		}
		appWorkflowDocumentRepository.save(appWorkflowDocument);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/previousStepDocWorkflow/{id}")
	public ResponseEntity<AppWorkflowDocument> backwordWFlow(@PathVariable Long id) {

		AppWorkflowDocument appWorkflowDocument = appWorkflowDocumentRepository.findById(id).get();

		System.out.println("Moving backward this Workflow Doc Id: " + id);

		if (appWorkflowDocument == null)
			return ResponseEntity.notFound().build();

		try {
			appWorkflowDocument.setWorkflowstatus(
					AppWorkFlowDocumentStatus.values()[appWorkflowDocument.getWorkflowstatus().ordinal() - 1]);
		} catch (java.lang.ArrayIndexOutOfBoundsException ex) {
			System.out.println("Reached first step...");
		}
		appWorkflowDocumentRepository.save(appWorkflowDocument);

		return ResponseEntity.noContent().build();
	}
	
	

	@DeleteMapping("/deleteallWFfiles")
	public ResponseEntity<HttpStatus> deleteAlldbfiles() {
		try {
			appWorkflowDocumentRepository.deleteAll();
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
