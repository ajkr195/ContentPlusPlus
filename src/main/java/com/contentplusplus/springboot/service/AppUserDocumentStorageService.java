package com.contentplusplus.springboot.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.exception.MyFileNotFoundException;
import com.contentplusplus.springboot.model.AppUserDocument;
import com.contentplusplus.springboot.repository.AppUserDocumentRepository;

@Service
public class AppUserDocumentStorageService {

	@Autowired
	private AppUserDocumentRepository dbFileRepository;

	public AppUserDocument storeFile(MultipartFile file) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			AppUserDocument dbFile = new AppUserDocument(UUID.randomUUID().toString(), fileName, file.getContentType(),
					file.getSize(), file.getBytes());

			return dbFileRepository.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public AppUserDocument getFile(Long id) {
		return dbFileRepository.findById(id)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
	}

}
