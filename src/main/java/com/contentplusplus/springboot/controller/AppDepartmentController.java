package com.contentplusplus.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppDepartmentController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@GetMapping("/listdepartment")
	public String listDepartment(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listdepartment");
			List<AppDepartment> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppDepartment> pageTuts = null;

			if (keyword != null) {
				pageTuts = appDepartmentRepository.findByDepartmentnameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appDepartmentRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appDepartmentRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "department_list";
	}
	
}
