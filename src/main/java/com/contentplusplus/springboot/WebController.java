package com.contentplusplus.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	
	@GetMapping ("/")
	String homePage(Model model) {
		model.addAttribute("pagename", "home");
		return "index";
	}
	
	@GetMapping ("/mydocuments")
	String docsPage(Model model) {
		model.addAttribute("pagename", "mydocuments");
		return "documentsuser";
	}
	
	@GetMapping ("/documents")
	String ordersPage(Model model) {
		model.addAttribute("pagename", "documents");
		return "documents";
	}
	
	@GetMapping ("/notifications")
	String notificationsPage(Model model) {
		model.addAttribute("pagename", "notifications");
		return "notifications";
	}
	
	@GetMapping ("/account")
	String accountPage(Model model) {
		model.addAttribute("pagename", "account");
		return "account";
	}
	
	@GetMapping ("/settings")
	String settingsPage(Model model) {
		model.addAttribute("pagename", "settings");
		return "settings";
	}
	
	@GetMapping ("/login")
	String loginPage() {
		return "login";
	}
	
	@GetMapping ("/signup")
	String helpPage() {
		return "signup";
	}
	
	@GetMapping ("/resetpassword")
	String resetpasswordPage() {
		return "reset-password";
	}
	
	@GetMapping ("/404")
	String notFoundPage() {
		return "404";
	}
	
	@GetMapping ("/help")
	String helpPage(Model model) {
		model.addAttribute("pagename", "help");
		return "help";
	}

}
