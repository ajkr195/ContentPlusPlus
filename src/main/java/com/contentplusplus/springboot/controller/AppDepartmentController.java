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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.service.AppDepartmentService;
import com.contentplusplus.springboot.validator.AppDepartmentAddValidator;
import com.contentplusplus.springboot.validator.AppDepartmentEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppDepartmentController {

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppDepartmentService appDepartmentService;

	@Autowired
	AppDepartmentAddValidator appDepartmentAddValidator;

	@Autowired
	AppDepartmentEditValidator appDepartmentEditValidator;

	@Autowired
	AppUserRepository appUserRepository;

	@ModelAttribute("users")
	public List<AppUser> initializeUsers() {
		return (List<AppUser>) appUserRepository.findAll();
	}

	@PostMapping(path = "/createdepartment")
	public String create(@RequestParam("departmentname") String departmentname,
			@RequestParam("useremail") String useremail, Model model) {

		AppDepartment department = new AppDepartment();
		AppUser appUser = appUserRepository.findByUseremailIgnoreCase(useremail);
		department.setDepartmentname(departmentname);
		//department.setDepartmentheadname(appUser.getUseremail());
		department.setDepartmentemaildistlist(appUser.getUseremail());
		department.setAppUser(appUser);
		if (null == appDepartmentRepository.findByDepartmentnameContainingIgnoreCase(departmentname)) {
			model.addAttribute("departmentname", departmentname);
			model.addAttribute("useremail", useremail);
			appDepartmentService.saveDepartment(department);
		} else {
			model.addAttribute("errormessage", "Department creation failed. Check the logs.");
			log.info("Department {} already exists.", departmentname);
		}
		return "department_list :: fragdepartment";
	}

	@GetMapping("/department")
	public String departmentNew(Model model) {
		String creatingdepartment = "creatingdepartment";
		model.addAttribute("creatingdepartment", creatingdepartment);
		model.addAttribute("department", new AppDepartment());
		return "department_new";
	}

	@PostMapping("/department")
	public String departmentNew(@Valid @ModelAttribute("department") AppDepartment department,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		model.addAttribute("department", department);
		String creatingdepartment = "creatingdepartment";
		model.addAttribute("creatingdepartment", creatingdepartment);
		appDepartmentAddValidator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			return "department_new";
		}
		appDepartmentService.saveDepartment(department);
		return "redirect:/listdepartment";
	}
	
	@RequestMapping(value = { "/departmenteditcasetypes/{id}" }, method = RequestMethod.GET)
	public String manageCaseTypesForDept(Model model, @PathVariable(required = false, name = "id") Long id) {
		model.addAttribute("pagename", "departmentedit");
		model.addAttribute("id", id);
		return "department_casetypes";
	}

	@RequestMapping(value = { "/departmentedit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editingdepartment = "editingdepartment";
		model.addAttribute("pagename", "departmentedit");
		model.addAttribute("editingdepartment", editingdepartment);
		model.addAttribute("department", appDepartmentRepository.findById(id));
		model.addAttribute("departmentheadname",
				appDepartmentRepository.findById(id).get().getAppUser().getUserfirstname() + " "
						+ appDepartmentRepository.findById(id).get().getAppUser().getUserlastname());
		model.addAttribute("departmentheademail",
				appDepartmentRepository.findById(id).get().getAppUser().getUseremail());
		return "department_edit";
	}
	
	@RequestMapping(value = "/departmentedit", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("department") AppDepartment department,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "departmentedit");
		String editingdepartment = "editingdepartment";
		model.addAttribute("editingdepartment", editingdepartment);
		model.addAttribute("department", department);
		appDepartmentEditValidator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			return "department_edit";
		}
		appDepartmentService.updateDepartment(department);
		return "redirect:/listdepartment";
	}

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
