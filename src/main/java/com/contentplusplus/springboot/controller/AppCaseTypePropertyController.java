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
import com.contentplusplus.springboot.model.AppCaseTypeProperty;
import com.contentplusplus.springboot.repository.AppCaseTypePropertyRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.service.AppCaseTypePropertyService;
import com.contentplusplus.springboot.validator.AppCaseTypePropertyEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AppCaseTypePropertyController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;
	
	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppCaseTypePropertyRepository appCaseTypePropertyRepository;
	
	@Autowired
	AppCaseTypePropertyEditValidator appCaseTypePropertyEditValidator;
	
	@Autowired
	AppCaseTypePropertyService appCaseTypePropertyService;
	
	@ModelAttribute("casetypes")
	public List<AppCaseType> initializecaseTypes() {
		return (List<AppCaseType>) appCaseTypeRepository.findAll();
	}

	@RequestMapping(value = { "/casetypepropEdit", "/casetypepropEdit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editingappCaseTypeProperty = "editingappCaseTypeProperty";
		model.addAttribute("pagename", "appCaseTypePropertyedit");
		//model.addAttribute("casetypename", appCaseTypeRepository.findById(id));
		model.addAttribute("editingappCaseTypeProperty", editingappCaseTypeProperty);
		if (null != id) {
		model.addAttribute("appCaseTypeProperty", appCaseTypePropertyRepository.findById(id));
		model.addAttribute("editingappCaseTypeProp", "editingappCaseTypeProp");
		
		} else {
			model.addAttribute("creatingappCaseTypeProp", "creatingappCaseTypeProp");
			model.addAttribute("appCaseTypeProperty", new AppCaseTypeProperty());
		}
		return "casetypeproperties_edit";
	}
	
	@RequestMapping(value = "/casetypepropEdit", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("appCaseTypeProperty") AppCaseTypeProperty appCaseTypeProperty,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "appCaseTypePropertyedit");
		String editingappCaseTypeProperty = "editingappCaseTypeProperty";
		model.addAttribute("editingappCaseTypeProperty", editingappCaseTypeProperty);
		model.addAttribute("appCaseTypeProperty", appCaseTypeProperty);
		appCaseTypePropertyEditValidator.validate(appCaseTypeProperty, bindingResult);
		if (bindingResult.hasErrors()) {
			return "casetypeproperties_edit";
		}
		try {
		appCaseTypePropertyService.updateappCaseType(appCaseTypeProperty);
		} catch (Exception ex) {
			appCaseTypePropertyService.saveAppCaseType(appCaseTypeProperty);
		}
		return "redirect:/listcasetypeprops";
	}
	
	@GetMapping("/listcasetypeprops")
	public String listDepartment(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listcasepropstype");
			List<AppCaseTypeProperty> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppCaseTypeProperty> pageTuts = null;

			if (keyword != null) {
				pageTuts = appCaseTypePropertyRepository.findByCasetypepropertynameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appCaseTypePropertyRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appCaseTypePropertyRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "casetypeproperties_list";
	}

}
