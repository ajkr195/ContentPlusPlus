package com.contentplusplus.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.service.AppCaseTypeService;
import com.contentplusplus.springboot.validator.AppCaseTypeEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AppCaseTypeController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;
	
	@Autowired
	AppCaseTypeEditValidator appCaseTypeEditValidator;
	
	@Autowired
	AppCaseTypeService appCaseTypeService;

	@RequestMapping(value = { "/casetypeEdit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editingappCaseType = "editingappCaseType";
		model.addAttribute("pagename", "appCaseTypeedit");
		model.addAttribute("editingappCaseType", editingappCaseType);
		model.addAttribute("casetypedepartmentname", appCaseTypeRepository.findById(id).get().getAppDepartment().getDepartmentname());
		model.addAttribute("appCaseType", appCaseTypeRepository.findById(id));
		return "casetype_edit";
	}
	
	@RequestMapping(value = "/casetypeEdit", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("appCaseType") AppCaseType appCaseType,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "appCaseTypeedit");
		String editingappCaseType = "editingappCaseType";
		model.addAttribute("editingappCaseType", editingappCaseType);
		model.addAttribute("appCaseType", appCaseType);
		appCaseTypeEditValidator.validate(appCaseType, bindingResult);
		if (bindingResult.hasErrors()) {
			return "appCaseType_edit";
		}
		appCaseTypeService.updateappCaseType(appCaseType);
		return "redirect:/listcasetype";
	}
	
	@GetMapping("/listcasetype")
	public String listDepartment(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listcasetype");
			List<AppCaseType> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppCaseType> pageTuts = null;

			if (keyword != null) {
				pageTuts = appCaseTypeRepository.findByCasetypenameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appCaseTypeRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appCaseTypeRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "casetype_list";
	}

}
