package com.contentplusplus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contentplusplus.springboot.model.AppCaseStatus;
import com.contentplusplus.springboot.repository.AppCaseDocumentRepository;
import com.contentplusplus.springboot.repository.AppCaseRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppDBContentRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.repository.AppFSContentRepository;
import com.contentplusplus.springboot.repository.AppInventoryRepository;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppCommonWebController {
	
	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;
	
	@Autowired
	AppCaseRepository appCaseRepository;
	
	@Autowired
	AppDBContentRepository appDBContentRepository;

	@Autowired
	AppFSContentRepository appFSContentRepository;
	
	@Autowired
	AppCaseDocumentRepository appCaseDocumentRepository;
	
	@Autowired
	AppInventoryRepository appInventoryRepository;
	
	
	
	@GetMapping({ "/index" })
	public String home(Model model) {
		model.addAttribute("pagename", "index");
		
		model.addAttribute("totalcases", appCaseRepository.count());
		model.addAttribute("totalnewcases", appCaseRepository.getAllNewCasesCount(AppCaseStatus.NEW));
		model.addAttribute("totalinprogresscases", appCaseRepository.getAllNewCasesCount(AppCaseStatus.IN_PROGRESS));
		model.addAttribute("totalclosedcases", appCaseRepository.getAllNewCasesCount(AppCaseStatus.CLOSED));
		model.addAttribute("totaldepartments", appDepartmentRepository.count());
		model.addAttribute("totalcasetypes", appCaseTypeRepository.count());
		model.addAttribute("totalusers", appUserRepository.count());
		model.addAttribute("totalroles", appRoleRepository.count());
		model.addAttribute("dbrepodocs", appDBContentRepository.count());
		model.addAttribute("fsrepodocs", appFSContentRepository.count());
		model.addAttribute("casedocs", appCaseDocumentRepository.count());
		model.addAttribute("totalservers", appInventoryRepository.count());
		
		
		return "dashboard";
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
	
	 @GetMapping("/downloadhelpfile")
	    // 1.
	    public ResponseEntity<Resource> downloadPdf() {
	        FileSystemResource resource = new FileSystemResource("./help/UserGuide.pdf");
	        // 2.
	        MediaType mediaType = MediaTypeFactory
	                .getMediaType(resource)
	                .orElse(MediaType.APPLICATION_OCTET_STREAM);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(mediaType);
	        // 3
	        ContentDisposition disposition = ContentDisposition
	                // 3.2
	                .attachment() //.inline() // or 
	                // 3.1
	                .filename(resource.getFilename())
	                .build();
	        headers.setContentDisposition(disposition);
	        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
