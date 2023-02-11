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
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.service.AppCaseTypeStepService;
import com.contentplusplus.springboot.validator.AppCaseTypeStepAddValidator;
import com.contentplusplus.springboot.validator.AppCaseTypeStepEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AppCaseTypeStepController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;
	
	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;
	
	@Autowired
	AppCaseTypeStepEditValidator appCaseTypeStepEditValidator;
	
	@Autowired
	AppCaseTypeStepAddValidator appCaseTypeStepAddValidator;
	
	@Autowired
	AppCaseTypeStepService appCaseTypeStepService;
	
	@ModelAttribute("casetypes")
	public List<AppCaseType> initializecaseTypes() {
		return (List<AppCaseType>) appCaseTypeRepository.findAll();
	}

	@RequestMapping(value = { "/casetypestepEdit", "/casetypestepEdit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		
		model.addAttribute("pagename", "appCaseTypeStepedit");
		
		if (null != id) {
		model.addAttribute("appCaseTypeStep", appCaseTypeStepRepository.findById(id));
		model.addAttribute("editingappCaseTypeProp", "editingappCaseTypeProp");
		
		} else {
			model.addAttribute("creatingappCaseTypeProp", "creatingappCaseTypeProp");
			model.addAttribute("appCaseTypeStep", new AppCaseTypeStep());
		}
		return "casetypesteps_edit";
	}
	
	@RequestMapping(value = "/casetypestepEdit", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("appCaseTypeStep") AppCaseTypeStep appCaseTypeStep,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "appCaseTypeStepedit");
		String editingappCaseTypeStep = "editingappCaseTypeStep";
		model.addAttribute("editingappCaseTypeStep", editingappCaseTypeStep);
		model.addAttribute("appCaseTypeStep", appCaseTypeStep);
		
		if ((null != appCaseTypeStep.getId()) &&  (bindingResult.hasErrors())) {
			appCaseTypeStepEditValidator.validate(appCaseTypeStep, bindingResult);
			return "casetypesteps_edit";
		} 
		if ((null == appCaseTypeStep.getId()) &&  (bindingResult.hasErrors())){
			appCaseTypeStepAddValidator.validate(appCaseTypeStep, bindingResult);
			return "casetypesteps_edit";
		}
		try {
		appCaseTypeStepService.updateappCaseTypeStep(appCaseTypeStep);
		} catch (Exception ex) {
			appCaseTypeStepService.saveAppCaseType(appCaseTypeStep);
		}
		return "redirect:/listcasetypesteps";
	}
	
	@GetMapping("/listcasetypesteps")
	public String listDepartment(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listcasetypesteps");
			List<AppCaseTypeStep> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppCaseTypeStep> pageTuts = null;

			if (keyword != null) {
				pageTuts = appCaseTypeStepRepository.findByCasetypestepnameContainingIgnoreCase(keyword, pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appCaseTypeStepRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appCaseTypeStepRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "casetypesteps_list";
	}

}
