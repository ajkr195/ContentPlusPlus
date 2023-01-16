package com.contentplusplus.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppDBContent;
import com.contentplusplus.springboot.model.AppFSContent;
import com.contentplusplus.springboot.model.AppPaginationModel;
import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.repository.AppDBContentRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.repository.AppFSContentRepository;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.repository.AppWorkflowDocumentRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppContetMgmtController {

	@Autowired
	AppDBContentRepository appDBContentRepository;

	@Autowired
	AppFSContentRepository appFSContentRepository;

	@RequestMapping(value = { "/documentsdbx" }, method = RequestMethod.GET, produces = "text/html")
	public String documentsdbList(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam(required = false) String keyword, @RequestParam("page") Optional<Integer> page, Model model) {

		int BUTTONS_TO_SHOW = 9;
		int INITIAL_PAGE = 0;
		int INITIAL_PAGE_SIZE = 8;
		int[] PAGE_SIZES = { 5, 8, 10, 15, 20, 25, 50, 100 };
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<AppDBContent> allfiles = null;

		if (keyword != null) {
			allfiles = appDBContentRepository.findByFileNameContainingIgnoreCase(keyword,
					PageRequest.of(evalPage, evalPageSize, Sort.by(Order.asc("id"))));
		} else {
			allfiles = appDBContentRepository.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by(Order.asc("id"))));
		}
		AppPaginationModel pager = new AppPaginationModel(allfiles.getTotalPages(), allfiles.getNumber(),
				BUTTONS_TO_SHOW);
		model.addAttribute("allfiles", allfiles);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("totalfiles", appDBContentRepository.count());
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("pagename", "documentsdb");
		return "documents_db";
	}
	
	@GetMapping("/documentsdb")
	public String documentsworkflowPagenew(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "documentsworkflow");
			List<AppDBContent> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppDBContent> pageTuts = null;

			if (keyword != null) {
				pageTuts = appDBContentRepository.findByFileNameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appDBContentRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appDBContentRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "documents_db";
	}
	
	
	@GetMapping("/documentsfs")
	public String documentsfsListnew(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "documentsworkflow");
			List<AppFSContent> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppFSContent> pageTuts = null;

			if (keyword != null) {
				pageTuts = appFSContentRepository.findByFilenameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appFSContentRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appFSContentRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "documents_fs";
	}


	@RequestMapping(value = { "/documentsfsx" }, method = RequestMethod.GET, produces = "text/html")
	public String documentsfsList(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam(required = false) String keyword, @RequestParam("page") Optional<Integer> page, Model model) {

		int BUTTONS_TO_SHOW = 9;
		int INITIAL_PAGE = 0;
		int INITIAL_PAGE_SIZE = 8;
		int[] PAGE_SIZES = { 5, 8, 10, 15, 20, 25, 50, 100 };
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<AppFSContent> allfiles = appFSContentRepository
				.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by(Order.asc("id"))));
		AppPaginationModel pager = new AppPaginationModel(allfiles.getTotalPages(), allfiles.getNumber(),
				BUTTONS_TO_SHOW);
		model.addAttribute("allfiles", allfiles);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("totalfiles", appFSContentRepository.count());
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("pagename", "documentsfs");
		return "documents_fs";
	}

}
