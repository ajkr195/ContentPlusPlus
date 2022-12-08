package com.contentplusplus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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

import com.contentplusplus.springboot.model.AppRole;
import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.model.AppUserDocument;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserDocumentRepository;
import com.contentplusplus.springboot.service.AppUserService;
import com.contentplusplus.springboot.validator.AppUserAddValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppUserWebController {

	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	AppUserDocumentRepository appUserDocumentRepository;

	@Autowired
	private AppUserAddValidator appUserNewValidator;

	private AppUserService userService;

	public AppUserWebController(AppUserService userService) {
		this.userService = userService;
	}

	@GetMapping({ "/", "/index" })
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/listuser")
	public String listRegisteredUsers(Model model) {
		model.addAttribute("appname", "Content++");
		List<AppUser> users = userService.findAllUsers();
		model.addAttribute("pagename", "listuser");
		model.addAttribute("users", users);
		return "list_user";
	}

	@GetMapping("/mydocuments")
	String docsPage(Model model) {
		model.addAttribute("pagename", "mydocuments");
		return "documentsuser";
	}

	@GetMapping("/documents")
	String ordersPage(Model model) {
		model.addAttribute("pagename", "documents");
		List<AppUserDocument> allfiles = appUserDocumentRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "documents";
	}

	@GetMapping("/notifications")
	String notificationsPage(Model model) {
		model.addAttribute("pagename", "notifications");
		return "notifications";
	}

	@GetMapping("/account")
	String accountPage(Model model) {
		model.addAttribute("pagename", "account");
		return "account";
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

	@GetMapping("/signup")
	String singupPage(Model model) {
		AppUser user = new AppUser();
		model.addAttribute("user", user);
		return "signup";
	}

	@ModelAttribute("roles")
	public List<AppRole> initializeRoles() {
		return (List<AppRole>) appRoleRepository.findAll();
	}

	@PostMapping("/signup/save")
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

		return "login";
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
