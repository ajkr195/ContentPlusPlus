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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.contentplusplus.springboot.model.AppRole;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.repository.AppDBContentRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.repository.AppFSContentRepository;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.service.AppUserService;
import com.contentplusplus.springboot.validator.AppUserAddValidator;
import com.contentplusplus.springboot.validator.AppUserEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppUserWebController {

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppDBContentRepository appUserDocumentRepository;

	@Autowired
	AppFSContentRepository appFSContentRepository;

	@Autowired
	private AppUserAddValidator appUserNewValidator;

	@Autowired
	private AppUserEditValidator appUserEditValidator;

	private AppUserService userService;

	public AppUserWebController(AppUserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("roles")
	public List<AppRole> initializeRoles() {
		return (List<AppRole>) appRoleRepository.findAll();
	}

	@ModelAttribute("departments")
	public List<AppDepartment> initializeDepartments() {
		return (List<AppDepartment>) appDepartmentRepository.findAll();
	}

	@RequestMapping(value = { "/adminuseredit/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editinguser = "editinguser";
		model.addAttribute("editinguser", editinguser);
		Optional<AppUser> appuser = appUserRepository.findById(id);
		model.addAttribute("userEmailid", appuser.get().getUseremail());
		model.addAttribute("user", appUserRepository.findById(id));
		return "user_edit";
	}

	@RequestMapping(value = "/adminuseredit", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult,
			HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());

		String editinguser = "creatinguser";
		model.addAttribute("editinguser", editinguser);
		model.addAttribute("user", user);
		appUserEditValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user_edit";
		}
		userService.updateUser(user);
		return "redirect:/listuser";
	}

	@GetMapping("/listuser")
	public String documentsworkflowPagenew(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "ALL") String userStatus,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listuser");
			List<AppUser> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppUser> pageTuts = null;

			if (keyword != null) {
				model.addAttribute("keyword", keyword);
			}

			if (userStatus != "") {
				model.addAttribute("userStatus", userStatus);
			}

			if (keyword == null && userStatus == "") {
				log.info("Inside keyword == null userStatus== null");
				pageTuts = appUserRepository.findAll(pageable);
			} else if (keyword == "" && userStatus == "") {
				log.info("Inside keyword == AND userStatus== condition");
				pageTuts = appUserRepository.findAll(pageable);
			} else if (keyword != null && userStatus.equalsIgnoreCase("active")) {
				log.info("Inside keyword != null AND userStatus== active condition");
				pageTuts = appUserRepository.findByUserenabledTrueAndUseremailContainingIgnoreCase(keyword, pageable);
			} else if (keyword != null && userStatus.equalsIgnoreCase("inactive")) {
				log.info("Inside keyword != null AND  userStatus== inactive condition");
				pageTuts = appUserRepository.findByUseremailContainingIgnoreCaseAndUserenabledFalse(keyword, pageable);
			} else if (keyword == null && userStatus.equalsIgnoreCase("active")) {
				log.info("Inside keyword == null AND userStatus== active condition");
				pageTuts = appUserRepository.findByUserenabledTrue(pageable);
			} else if (keyword == null && userStatus.equalsIgnoreCase("inactive")) {
				log.info("Inside keyword == null AND userStatus== inactive condition");
				pageTuts = appUserRepository.findByUserenabledFalse(pageable);
			} else if (keyword != null && userStatus == null) {
				log.info("Inside keyword == null AND userStatus== inactive condition");
				pageTuts = appUserRepository.findByUseremailContainingIgnoreCase(keyword, pageable);
			} else if (userStatus.equalsIgnoreCase("all")) {
				pageTuts = appUserRepository.findAll(pageable);
			}

			allfiles = pageTuts.getContent();

			model.addAttribute("users", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalusers", appUserRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "user_list";
	}

	@GetMapping("/mydocuments")
	String docsPage(Model model) {
		model.addAttribute("pagename", "mydocuments");
		return "documentsuser";
	}

	@GetMapping("/signup")
	public String userRegistrationsd(Model model) {
		String creatinguser = "creatinguser";
		model.addAttribute("creatinguser", creatinguser);
		model.addAttribute("user", new AppUser());
		return "signup";
	}

	@PostMapping("/signup")
	public String userRegistration(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult,
			HttpServletRequest request, Model model) {

		model.addAttribute("user", user);
		String creatinguser = "creatinguser";
		model.addAttribute("creatinguser", creatinguser);
		appUserNewValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "signup";
		}
		userService.saveUser(user);
		return "redirect:/login?regnsuccess";
	}
	
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
