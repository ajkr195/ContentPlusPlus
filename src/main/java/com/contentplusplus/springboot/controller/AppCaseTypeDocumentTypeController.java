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
import com.contentplusplus.springboot.model.AppCaseTypeDocumentType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.repository.AppCaseTypeDocumentTypeRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.service.AppCaseTypeDocumentTypeService;
import com.contentplusplus.springboot.validator.AppCaseTypeDocTypeAddValidator;
import com.contentplusplus.springboot.validator.AppCaseTypeStepAddValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AppCaseTypeDocumentTypeController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppCaseTypeDocumentTypeRepository appCaseTypeDocumentTypeRepository;

	@Autowired
	AppCaseTypeDocTypeAddValidator appCaseTypeDocTypeAddValidator;

	@Autowired
	AppCaseTypeStepAddValidator appCaseTypeStepAddValidator;

	@Autowired
	AppCaseTypeDocumentTypeService appCaseTypeDocumentTypeService;

	@ModelAttribute("casetypes")
	public List<AppCaseType> initializecaseTypes() {
		return (List<AppCaseType>) appCaseTypeRepository.findAll();
	}

	@RequestMapping(value = { "/casetypeDocTypeEdit", "/casetypeDocTypeEdit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editingappCaseTypeStep = "editingappCaseTypeDocType";
		model.addAttribute("pagename", "appCaseTypeDocTypeedit");
		// model.addAttribute("casetypename", appCaseTypeRepository.findById(id));
		model.addAttribute("editingappCaseTypeDocType", editingappCaseTypeStep);
		if (null != id) {
			model.addAttribute("appCaseTypeDocType", appCaseTypeDocumentTypeRepository.findById(id));
			model.addAttribute("editingappCaseTypeDocType", "editingappCaseTypeDocType");

		} else {
			model.addAttribute("creatingappCaseTypeDocType", "creatingappCaseTypeDocType");
			model.addAttribute("appCaseTypeDocType", new AppCaseTypeDocumentType());
		}
		return "casetypedoctype_edit";
	}

	@RequestMapping(value = "/casetypeDocTypeEdit", method = RequestMethod.POST)
	public String adminusereditRegistration(
			@Valid @ModelAttribute("appCaseTypeDocumentType") AppCaseTypeDocumentType appCaseTypeDocumentType,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "appCaseTypeDocTypeedit");
		String editingappCaseTypeType = "editingappCaseTypeType";
		model.addAttribute("editingappCaseTypeStep", editingappCaseTypeType);
		model.addAttribute("appCaseTypeDocumentType", appCaseTypeDocumentType);

		if ((null != appCaseTypeDocumentType.getId()) && (bindingResult.hasErrors())) {
			appCaseTypeDocTypeAddValidator.validate(appCaseTypeDocumentType, bindingResult);
			return "casetypedoctype_edit";
		}
		if ((null == appCaseTypeDocumentType.getId()) && (bindingResult.hasErrors())) {
			appCaseTypeStepAddValidator.validate(appCaseTypeDocumentType, bindingResult);
			return "casetypedoctype_edit";
		}
		try {
			appCaseTypeDocumentTypeService.updateappCaseTypeDocumentType(appCaseTypeDocumentType);
		} catch (Exception ex) {
			appCaseTypeDocumentTypeService.saveAppCaseTypeDocType(appCaseTypeDocumentType);
		}
		return "redirect:/listcasetypedoctypes";
	}

	@GetMapping("/listcasetypedoctypes")
	public String listDepartment(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listcasetypedoctypes");
			List<AppCaseTypeDocumentType> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppCaseTypeDocumentType> pageTuts = null;

			if (keyword != null) {
				pageTuts = appCaseTypeDocumentTypeRepository.findByCasetypedocumenttypenameContainingIgnoreCase(keyword,
						pageable);
				model.addAttribute("keyword", keyword);
			} else {
				pageTuts = appCaseTypeDocumentTypeRepository.findAll(pageable);
			}
			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appCaseTypeDocumentTypeRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "casetypedoctypes_list";
	}

}
