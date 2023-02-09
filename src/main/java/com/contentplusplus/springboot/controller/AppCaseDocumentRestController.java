package com.contentplusplus.springboot.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseDocument;
import com.contentplusplus.springboot.model.AppCaseHistory;
import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;
import com.contentplusplus.springboot.payload.AppContentUploadResponse;
import com.contentplusplus.springboot.repository.AppCaseDocumentRepository;
import com.contentplusplus.springboot.repository.AppCaseHistoryRepository;
import com.contentplusplus.springboot.repository.AppCaseRepository;
import com.contentplusplus.springboot.repository.AppDBContentRepository;
import com.contentplusplus.springboot.service.AppDBContentStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class AppCaseDocumentRestController {

	@Autowired
	private AppDBContentStorageService appContentStorageService;

	@Autowired
	AppDBContentRepository appContentRepository;

	@Autowired
	AppCaseRepository appCaseRepository;

	@Autowired
	AppCaseDocumentRepository appCaseDocumentRepository;

	@Autowired
	AppCaseHistoryRepository appCaseHistoryRepository;

	@GetMapping("/getCaseTypeDocTypes/{caseId}")
	public ResponseEntity<List<AppCaseTypeDocumentType>> getCaseDocTypes(@PathVariable Long caseId) {
		// Load file from database
		AppCase appCase = appCaseRepository.findById(caseId).get();

		AppCaseType appCaseType = appCase.getAppCaseType();

		List<AppCaseTypeDocumentType> acdc = appCaseType.getCaseTypeDocumentTypeList();

		return new ResponseEntity<>(acdc, HttpStatus.OK);
	}

	@PostMapping("/uploadCaseFile")
	public AppContentUploadResponse uploadCaseFile(@RequestParam("file") MultipartFile file,
			@RequestParam("doctype") String doctype, @RequestParam("caseid") String caseid) {
		AppCaseDocument appFile = appContentStorageService.storeCaseFile(file, doctype, caseid);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadCaseFile/")
				.path(appFile.getId().toString()).toUriString();
		log.info("Uploading file: {}", appFile.getFileName());

		AppCase appCase = appCaseRepository.findById(Long.parseLong(caseid)).get();

		appCaseHistoryRepository.save(
				new AppCaseHistory(getPrincipal() + " - Add file to this Case - " + appFile.getFileName(), appCase));

		return new AppContentUploadResponse(appFile.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@PostMapping("/uploadCaseMultipleFiles")
	public List<AppContentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam("doctype") String doctype, @RequestParam("caseid") String caseid) {

		// System.out.println("doctype:: "+ doctype);

		return Arrays.asList(files).stream()
				// .peek(fileBeingProcessed -> log.info("Processing File : {} ",
				// fileBeingProcessed.getName()))
				.map(file -> uploadCaseFile(file, doctype, caseid)).collect(Collectors.toList());
	}

	@GetMapping("/downloadCaseFile/{fileId}")
	public ResponseEntity<Resource> downloadCaseFile(@PathVariable Long fileId) {
		// Load file from database
		AppCaseDocument appCaseContent = appContentStorageService.getCaseFile(fileId);
		
		AppCase appCase = appCaseRepository.findById(appCaseContent.getAppCase().getId()).get();
		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Downloaded case file - " + appCaseContent.getFileName(), appCase));

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appCaseContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appCaseContent.getFileName() + "\"")
				.body(new ByteArrayResource(appCaseContent.getFileData()));
	}

	@GetMapping("/viewCaseFile/{fileId}")
	public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
		// Load file from database
		AppCaseDocument appCaseContent = appContentStorageService.getCaseFile(fileId);
		AppCase appCase = appCaseRepository.findById(appCaseContent.getAppCase().getId()).get();
		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Viewed case file - " + appCaseContent.getFileName(), appCase));

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(appCaseContent.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + appCaseContent.getFileName() + "\"")
				.body(new ByteArrayResource(appCaseContent.getFileData()));
	}

	@DeleteMapping("/deleteCasefile/{id}")
	public ResponseEntity<HttpStatus> deletedbfile(@PathVariable("id") Long id) {
		try {
			appCaseDocumentRepository.deleteById(id);
			
			AppCaseDocument acd = appCaseDocumentRepository.findById(id).get();
			
			AppCase appCase = appCaseRepository.findById(acd.getAppCase().getId()).get();
			appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Deleted case file - " + acd.getFileName(), appCase));
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteallCasefiles")
	public ResponseEntity<HttpStatus> deleteAlldbfiles() {
		try {
			appCaseDocumentRepository.deleteAll();

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
