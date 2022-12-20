package com.contentplusplus.springboot.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.model.AppFSContent;

public interface AppFilesSystemStorageService {
	public void init();

	public AppFSContent save(MultipartFile file);

	public Resource load(String filename);

	public void deleteAll();
	
	
	public void deleteFSfile(String filename);
	

	public Stream<Path> loadAll();
}
