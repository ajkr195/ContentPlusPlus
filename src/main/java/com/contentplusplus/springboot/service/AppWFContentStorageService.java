package com.contentplusplus.springboot.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.exception.MyFileNotFoundException;
import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.repository.AppWorkflowDocumentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppWFContentStorageService {

	@Autowired
	private AppWorkflowDocumentRepository appWorkflowDocumentRepository;

	public AppWorkflowDocument storeWFFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			AppWorkflowDocument appFile = new AppWorkflowDocument(fileName, file.getContentType(), file.getSize(),
					file.getBytes());

			appFile.setWorkflowstatus(AppWorkFlowDocumentStatus.DRAFT);

			return appWorkflowDocumentRepository.save(appFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public AppWorkflowDocument getWFFile(Long id) {
		return appWorkflowDocumentRepository.findById(id)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
	}

	public AppWorkflowDocument updateWFFile(MultipartFile file, Long id) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			AppWorkflowDocument appFile = appWorkflowDocumentRepository.findById(id).get();

			System.out.println("Going to update this: " + appFile.getId());
			appFile.setFileName(fileName);
			appFile.setFileType(file.getContentType());
			appFile.setFileData(file.getBytes());
			appFile.setFileSize(file.getSize());
			return appWorkflowDocumentRepository.save(appFile);
			// return appWorkflowDocumentRepository.updateWFDocumentContent(file.getBytes(),
			// id);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

}
