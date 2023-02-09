package com.contentplusplus.springboot.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.exception.MyFileNotFoundException;
import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseDocument;
import com.contentplusplus.springboot.model.AppDBContent;
import com.contentplusplus.springboot.repository.AppCaseDocumentRepository;
import com.contentplusplus.springboot.repository.AppCaseRepository;
import com.contentplusplus.springboot.repository.AppDBContentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppDBContentStorageService {

	@Autowired
	private AppDBContentRepository appContentRepository;
	
	@Autowired
	private AppCaseDocumentRepository appCaseDocumentRepository;
	
	@Autowired
	private AppCaseRepository appCaseRepository;


	public AppDBContent storeFileInDB(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			AppDBContent appFile = new AppDBContent(fileName, file.getContentType(),
					file.getSize(), file.getBytes());

			return appContentRepository.save(appFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public AppDBContent getFile(Long id) {
		return appContentRepository.findById(id)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
	}
	
	public AppCaseDocument storeCaseFile(MultipartFile file, String doctype, String caseid) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			
			Long tgtcaseid = Long.parseLong(caseid);
			
			AppCase appCase = appCaseRepository.findById(tgtcaseid).get();
			
			AppCaseDocument appFile = new AppCaseDocument(fileName, doctype, file.getContentType(), 
					file.getSize(), file.getBytes(), appCase);

			return appCaseDocumentRepository.save(appFile); 
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex); 
		}
	}
	
	public AppCaseDocument getCaseFile(Long id) {
		return appCaseDocumentRepository.findById(id)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
	}

}
