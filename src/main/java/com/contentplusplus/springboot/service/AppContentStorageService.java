package com.contentplusplus.springboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.exception.MyFileNotFoundException;
import com.contentplusplus.springboot.model.AppContent;
import com.contentplusplus.springboot.repository.AppContentRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppContentStorageService {

	@Autowired
	private AppContentRepository appContentRepository;

	private final Path root = Paths.get("./uploadloc");

	@PostConstruct
	public void initFileSystem() {
		try {
			log.info("Initializing filestorage location: {}", root.toAbsolutePath());
			Files.createDirectories(root);
			log.info("Initializof filestorage location: {} was successful.", root.toAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	public void storeFileInFileSystemStorage(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("A file of that name already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteAllFromFileSystemStorage() {
		//FileSystemUtils.deleteRecursively(root.toFile()); // Good but not good
		try {
			//FileUtils.cleanDirectory(new File(root.toAbsolutePath().toString()));  // Okay
			Files.walk(Paths.get(root.toAbsolutePath().toString()))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .filter(item -> !item.getPath().equals(root.toAbsolutePath().toString()))
            .forEach(File::delete);  // Alternate
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error deleting files from : {}", root.toAbsolutePath().toString());
			e.printStackTrace();
		} 
	}

	public Stream<Path> loadAllFromFileSystemStorage() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	public AppContent storeFileInDB(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			AppContent appFile = new AppContent(UUID.randomUUID().toString(), fileName, file.getContentType(),
					file.getSize(), file.getBytes());

			return appContentRepository.save(appFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public AppContent getFile(Long id) {
		return appContentRepository.findById(id)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
	}

}
