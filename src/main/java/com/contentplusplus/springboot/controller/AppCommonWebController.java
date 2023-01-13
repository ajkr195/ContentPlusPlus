package com.contentplusplus.springboot.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppCommonWebController {

	@GetMapping({ "/index" })
	public String home(Model model) {
		model.addAttribute("pagename", "index");
		return "dashboard";
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


	@GetMapping("/login")
	public String login(Model model, String error, String logout, String regnsuccess) {

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("logout", "You have been logged out successfully.");

		if (regnsuccess != null)
			model.addAttribute("regnsuccess", "Registration was successful. You can login now.");

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

//	private String getPrincipal() {
//		String userName = null;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof UserDetails) {
//			userName = ((UserDetails) principal).getUsername();
//		} else {
//			userName = principal.toString();
//		}
//		return userName;
//	}

}
