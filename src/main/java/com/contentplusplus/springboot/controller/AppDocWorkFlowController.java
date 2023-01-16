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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.repository.AppWorkflowDocumentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppDocWorkFlowController {

	@Autowired
	AppWorkflowDocumentRepository appWorkflowDocumentRepository;

//	@GetMapping("/documentsworkflow")
//	String documentsworkflowList(Model model) {
//		model.addAttribute("pagename", "documentsworkflow");
//		model.addAttribute("allfiles", appWorkflowDocumentRepository.findAll());
//		return "documents_workflow";
//	}

	@GetMapping("/documentsworkflow")
	public String documentsworkflowPagenew(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "DRAFT") String flowStatus,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "documentsworkflow");
			List<AppWorkflowDocument> allfiles = new ArrayList<AppWorkflowDocument>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppWorkflowDocument> pageTuts = null;

			if (keyword == null && flowStatus == null) {
				pageTuts = appWorkflowDocumentRepository.findAll(pageable);
			} else if (keyword != null && flowStatus == null) {
				pageTuts = appWorkflowDocumentRepository.findByFileNameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else if (keyword == null && flowStatus != null) {
				pageTuts = appWorkflowDocumentRepository
						.findByWorkflowstatus(AppWorkFlowDocumentStatus.valueOf(flowStatus), pageable);
				model.addAttribute("flowStatus", flowStatus);
			} else if (keyword != null && flowStatus != null) {
				pageTuts = appWorkflowDocumentRepository.findByWorkflowstatusAndFileNameContainingIgnoreCase(
						AppWorkFlowDocumentStatus.valueOf(flowStatus), keyword, pageable);
				model.addAttribute("keyword", keyword);
				model.addAttribute("flowStatus", flowStatus);
			} else {
				pageTuts = appWorkflowDocumentRepository.findAll(pageable);
			}

			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appWorkflowDocumentRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "documents_workflow";
	}

	@GetMapping("/documentsworkflowall")
	public String documentsworkflowPageAll(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			List<AppWorkflowDocument> allfiles = new ArrayList<AppWorkflowDocument>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppWorkflowDocument> pageTuts = null;

			pageTuts = appWorkflowDocumentRepository.findAll(pageable);

			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appWorkflowDocumentRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "documents_workflow";
	}

}
