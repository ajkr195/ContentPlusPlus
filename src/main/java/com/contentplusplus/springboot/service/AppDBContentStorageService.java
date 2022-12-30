package com.contentplusplus.springboot.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.exception.MyFileNotFoundException;
import com.contentplusplus.springboot.model.AppDBContent;
import com.contentplusplus.springboot.repository.AppDBContentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppDBContentStorageService {

	@Autowired
	private AppDBContentRepository appContentRepository;


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

}
