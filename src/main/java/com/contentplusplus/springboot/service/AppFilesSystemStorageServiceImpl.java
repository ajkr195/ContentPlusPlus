package com.contentplusplus.springboot.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.exception.FileStorageException;
import com.contentplusplus.springboot.model.AppDBContent;
import com.contentplusplus.springboot.model.AppFSContent;
import com.contentplusplus.springboot.repository.AppFSContentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppFilesSystemStorageServiceImpl implements AppFilesSystemStorageService {

	@Autowired
	private AppFSContentRepository appFSContentRepository;

	private final Path root = Paths.get("uploads");

	@Override
	public void init() {
		if (Files.notExists(root)) {
			try {
				Files.createDirectory(root);
			} catch (IOException e) {
				throw new RuntimeException("Could not initialize folder for upload!");
			}
		} else {
			log.info("Upload location already exists...");
		}
	}

	//@Override
	public AppFSContent savess(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/fsfile/")
				.path(file.getName()).toUriString();
		AppFSContent appFile = new AppFSContent(file.getName(), fileDownloadUri);

		return appFSContentRepository.save(appFile);
	}
	@Override
	public AppFSContent save(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
		} catch (Exception ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		AppFSContent appFile = new AppFSContent(fileName, ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/fsfile/").path(file.getOriginalFilename()).toUriString(),file.getContentType(),
				file.getSize());
		return appFSContentRepository.save(appFile);

	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		// FileSystemUtils.deleteRecursively(root.toFile()); // Good but not good
		try {
			// FileUtils.cleanDirectory(new File(root.toAbsolutePath().toString())); // Okay
			Files.walk(Paths.get(root.toAbsolutePath().toString())).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.filter(item -> !item.getPath().equals(root.toAbsolutePath().toString())).forEach(File::delete); // Alternate
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error deleting files from : {}", root.toAbsolutePath().toString());
			e.printStackTrace();
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
}