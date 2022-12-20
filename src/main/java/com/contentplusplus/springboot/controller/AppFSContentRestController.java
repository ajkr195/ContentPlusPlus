package com.contentplusplus.springboot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.exception.ResourceNotFoundException;
import com.contentplusplus.springboot.model.AppFSContent;
import com.contentplusplus.springboot.payload.AppContentUploadResponse;
import com.contentplusplus.springboot.payload.ResponseMessage;
import com.contentplusplus.springboot.repository.AppFSContentRepository;
import com.contentplusplus.springboot.service.AppFilesSystemStorageService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class AppFSContentRestController {

	@Autowired
	AppFilesSystemStorageService storageService;

	@Autowired
	AppFSContentRepository appFSContentRepository;
	
	@PostConstruct
	public void initializeStorageLoc () {
		storageService.init();
	}

	
	@PostMapping("/fsuploadsingle")
	public AppContentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
		AppFSContent appFile = storageService.save(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/fsfile/")
				.path(appFile.getId().toString()).toUriString();
		log.info("Uploading file: {}", appFile.getFilename());

		return new AppContentUploadResponse(appFile.getFilename(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	@PostMapping("/fsupload")
	public List<AppContentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

		return Arrays.asList(files).stream()
				// .peek(fileBeingProcessed -> log.info("Processing File : {} ",
				// fileBeingProcessed.getName()))
				.map(file -> uploadFile(file)).collect(Collectors.toList());
	}
	
	@PostMapping(
			  path = "/fsuploadss",
			  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<ResponseMessage> uploadssFiles(@RequestParam("files") MultipartFile[] files) {
		String message = "";
		try {
			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {
				log.info("Uploading file: ", file.getName());
				storageService.save(file);
				appFSContentRepository.save(new AppFSContent(file.getName(),
						MvcUriComponentsBuilder
								.fromMethodName(AppFSContentRestController.class, "getFile", file.getName()).build()
								.toString()));
				fileNames.add(file.getOriginalFilename());
			});

			message = "Uploaded the files successfully: " + fileNames;
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Fail to upload files!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/fsfiles")
	public ResponseEntity<List<AppFSContent>> getListFiles() {
		List<AppFSContent> fileInfos = storageService.loadAll().map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(AppFSContentRestController.class, "getFile", path.getFileName().toString()).build()
					.toString();

			return new AppFSContent(filename, url);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@GetMapping("/fsfile/{filename}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/fsviewfile/{filename:.+}")
	public ResponseEntity<Resource> viewFsFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@DeleteMapping("/fsdelfile/{id}")
	  public ResponseEntity<HttpStatus> delFSFile(@PathVariable("id") long id) {
	    try {
	    	
	    	AppFSContent fsRecord = appFSContentRepository.findById(id).orElseThrow();
	    	
	    	appFSContentRepository.deleteById(id);
	    	storageService.deleteFSfile(fsRecord.getFilename());
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@DeleteMapping("/deleteAllFSfiles")
	public ResponseEntity<HttpStatus> deleteAlldbfiles() {
		try {
			appFSContentRepository.deleteAll();
			storageService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}