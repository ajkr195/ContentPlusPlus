package com.contentplusplus.springboot.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentplusplus.springboot.helper.AppInventoryHelper;
import com.contentplusplus.springboot.model.AppInventory;
import com.contentplusplus.springboot.repository.AppInventoryRepository;
import com.contentplusplus.springboot.service.AppInventoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@Controller
@RequestMapping("/api/csv")
public class AppInventoryController {

	@Autowired
	AppInventoryService fileService;
	
	@Autowired
	AppInventoryRepository appInventoryRepository;
	
	@GetMapping("/inventory")
	String inventoryPage(Model model) {
		model.addAttribute("pagename", "inventory");
		return "inventory";
	}

	@PostMapping("/upload")
	public ResponseEntity<AppInventoryResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (AppInventoryHelper.hasCSVFormat(file)) {
			try {
				fileService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/csv/download/")
						.path(file.getOriginalFilename()).toUriString();

				return ResponseEntity.status(HttpStatus.OK).body(new AppInventoryResponseMessage(message, fileDownloadUri));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new AppInventoryResponseMessage(message, ""));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AppInventoryResponseMessage(message, ""));
	}

	/*@GetMapping("/inventories")
	public ResponseEntity<List<AppInventory>> getAllTutorials() {
		try {
			List<AppInventory> tutorials = fileService.getAllInvetories();

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		InputStreamResource file = new InputStreamResource(fileService.load()); 

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
	
	@GetMapping("/listinventory")
	public String inventoryList(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "ALL") String objStatus,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listinventory");
			List<AppInventory> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppInventory> pageTuts = null;

			if (keyword != null) {
				model.addAttribute("keyword", keyword);
			}

			if (objStatus != "") {
				model.addAttribute("objStatus", objStatus);
			}

			if (keyword == null && objStatus == "") {
				log.info("Inside keyword == null objStatus== null");
				pageTuts = appInventoryRepository.findAll(pageable);
			} else if (keyword == "" && objStatus == "") {
				log.info("Inside keyword == AND objStatus== condition");
				pageTuts = appInventoryRepository.findAll(pageable);
			} else if (keyword != null && objStatus.equalsIgnoreCase("active")) {
				log.info("Inside keyword != null AND objStatus== active condition");
				pageTuts = appInventoryRepository.findByActiveTrueAndTitleContainingIgnoreCase(keyword, pageable);
			} else if (keyword != null && objStatus.equalsIgnoreCase("inactive")) {
				log.info("Inside keyword != null AND  objStatus== inactive condition");
				pageTuts = appInventoryRepository.findByTitleContainingIgnoreCaseAndActiveFalse(keyword, pageable);
			} else if (keyword == null && objStatus.equalsIgnoreCase("active")) {
				log.info("Inside keyword == null AND objStatus== active condition");
				pageTuts = appInventoryRepository.findByActiveTrue(pageable);
			} else if (keyword == null && objStatus.equalsIgnoreCase("inactive")) {
				log.info("Inside keyword == null AND objStatus== inactive condition");
				pageTuts = appInventoryRepository.findByActiveFalse(pageable);
			} else if (keyword != null && objStatus == null) {
				log.info("Inside keyword == null AND objStatus== inactive condition");
				pageTuts = appInventoryRepository.findByTitleContainingIgnoreCase(keyword, pageable);
			} else if (objStatus.equalsIgnoreCase("all")) {
				pageTuts = appInventoryRepository.findAll(pageable);
			} else {
				pageTuts = appInventoryRepository.findAll(pageable);
			}
			
			allfiles = pageTuts.getContent();

			model.addAttribute("inventories", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalinventory", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalitems", appInventoryRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "inventory";
	}
	
	@DeleteMapping("/deleteInventoryItem/{id}")
	public ResponseEntity<HttpStatus> deletedbfile(@PathVariable("id") Long id) {
		try {
			appInventoryRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
