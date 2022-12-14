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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contentplusplus.springboot.model.AppDBContent;
import com.contentplusplus.springboot.model.AppFSContent;
import com.contentplusplus.springboot.model.AppPaginationModel;
import com.contentplusplus.springboot.model.AppRole;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.model.AppWorkFlowDocumentStatus;
import com.contentplusplus.springboot.model.AppWorkflowDocument;
import com.contentplusplus.springboot.repository.AppDBContentRepository;
import com.contentplusplus.springboot.repository.AppFSContentRepository;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.repository.AppWorkflowDocumentRepository;
import com.contentplusplus.springboot.service.AppUserService;
import com.contentplusplus.springboot.validator.AppUserAddValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppWebController {

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppDBContentRepository appUserDocumentRepository;

	@Autowired
	AppWorkflowDocumentRepository appWorkflowDocumentRepository;

	@Autowired
	AppFSContentRepository appFSContentRepository;

	@Autowired
	private AppUserAddValidator appUserNewValidator;

	private AppUserService userService;

	public AppWebController(AppUserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("roles")
	public List<AppRole> initializeRoles() {
		return (List<AppRole>) appRoleRepository.findAll();
	}

	@GetMapping({ "/index" })
	public String home(Model model) {
		model.addAttribute("pagename", "index");
		return "dashboard";
	}

	@GetMapping("/listuser")
	public String listRegisteredUsers(Model model) {
		model.addAttribute("appname", "Content++");
		// List<AppUser> users = userService.findAllUsers();
		model.addAttribute("pagename", "listuser");
		model.addAttribute("users", userService.findAllUsers());
		model.addAttribute("activeusers", userService.findAllActiveUsers());
		model.addAttribute("inactiveusers", userService.findAllInActiveUsers());
		return "list_user";
	}

	@RequestMapping(value = { "/listuser2" }, method = RequestMethod.GET, produces = "text/html")
	public String showuserList(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) {

		int BUTTONS_TO_SHOW = 9;
		int INITIAL_PAGE = 0;
		int INITIAL_PAGE_SIZE = 8;
		int[] PAGE_SIZES = { 5, 8, 10, 15, 20, 25, 50, 100 };
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<AppUser> users = appUserRepository
				.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by(Order.asc("id"))));
		AppPaginationModel pager = new AppPaginationModel(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);
		model.addAttribute("users", users);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("totalusers", appUserRepository.count());
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("pagename", "listuser2");
		return "list_user2";
	}

	@GetMapping("/mydocuments")
	String docsPage(Model model) {
		model.addAttribute("pagename", "mydocuments");
		return "documentsuser";
	}

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
			}

			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
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
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "documents_workflow";
	}

	@GetMapping("/documentsdb")
	String dbDocs(Model model) {
		model.addAttribute("pagename", "documentsdb");
		List<AppDBContent> allfiles = appUserDocumentRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "documents_db";
	}

	@RequestMapping(value = { "/documentsdb2" }, method = RequestMethod.GET, produces = "text/html")
	public String documentsdbList(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) {

		int BUTTONS_TO_SHOW = 9;
		int INITIAL_PAGE = 0;
		int INITIAL_PAGE_SIZE = 8;
		int[] PAGE_SIZES = { 5, 8, 10, 15, 20, 25, 50, 100 };
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<AppDBContent> allfiles = appUserDocumentRepository
				.findAll(PageRequest.of(evalPage, evalPageSize, Sort.by(Order.asc("id"))));
		AppPaginationModel pager = new AppPaginationModel(allfiles.getTotalPages(), allfiles.getNumber(),
				BUTTONS_TO_SHOW);
		model.addAttribute("allfiles", allfiles);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("totalfiles", appUserDocumentRepository.count());
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("pagename", "documentsdb");
		return "documents_db2";
	}

	@GetMapping("/documentsfs")
	String fsDocs(Model model) {
		model.addAttribute("pagename", "documentsfs");
		List<AppFSContent> allfiles = appFSContentRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "documents_fs";
	}

	@RequestMapping(value = { "/documentsfs2" }, method = RequestMethod.GET, produces = "text/html")
	public String documentsfsList(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) {

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
		return "documents_fs2";
	}

	@GetMapping("/notifications")
	String notificationsPage(Model model) {
		model.addAttribute("pagename", "notifications");
		return "notifications";
	}

	@GetMapping("/home")
	String home2Page(Model model) {
		model.addAttribute("pagename", "home");
		return "home";
	}

	@GetMapping("/settings")
	String settingsPage(Model model) {
		model.addAttribute("pagename", "settings");
		return "settings";
	}

	@GetMapping("/resetpassword")
	String resetpasswordPage(Model model) {
		return "reset-password";
	}

	@GetMapping("/help")
	String helpPage(Model model) {
		model.addAttribute("pagename", "help");
		return "help";
	}

	@GetMapping({ "/" })
	String homePage(Model model) {
		return "redirect:/home";
	}

	@GetMapping({ "/steps" })
	String stepsPage(Model model) {
		model.addAttribute("pagename", "steps");
		return "steps";
	}

	@GetMapping({ "/activity" })
	String activityPage(Model model) {
		model.addAttribute("pagename", "activity");
		return "activity";
	}

	@GetMapping("/signup")
	String singupPage(Model model) {
		AppUser user = new AppUser();
		model.addAttribute("user", user);
		return "signup";
	}

	@PostMapping("/signup")
	public String registrationsignup(@Valid @ModelAttribute("user") AppUser user, BindingResult result, Model model) {
		AppUser existing = userService.findByUseremailIgnoreCase(user.getUseremail());
		if (existing != null) {
			result.rejectValue("useremail", null, "This email id is associated with an account already.");
		}

		appUserNewValidator.validate(user, result);

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "signup";
		}

		userService.saveUser(user);

		return "redirect:/signup?success";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("logout", "You have been logged out successfully.");

		return "signin";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);

			SecurityContextHolder.getContext().setAuthentication(null);
			// session.invalidate();
		}
		return "redirect:login?logout";
	}

}
