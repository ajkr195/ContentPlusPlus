package com.contentplusplus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contentplusplus.springboot.model.AppUser;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.service.AppUserService;
import com.contentplusplus.springboot.validator.AppUserAddValidator;

@RestController

public class AppUserRestController {

	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	private AppUserAddValidator appUserNewValidator;
	
	private AppUserService userService;

	public AppUserRestController(AppUserService userService) {
		this.userService = userService;
	}


	@GetMapping("/listAllActiveUsers")
	List<AppUser> activeUsers() {
		return appUserRepository.findByUserenabledTrue();
	}
	
	@GetMapping("/listAllInActiveUsers")
	List<AppUser> inActiveUsers() {
		return appUserRepository.findByUserenabledFalse();
	}
	
	@PostMapping(value = "/deactivateUser/{id}")
	public String deactivateUser(@PathVariable Long id) {
		System.out.println("De-Activating User :: " + id);
		appUserRepository.setAppUserAsInActiveById(id);
		return "OK!";
	}

	@PostMapping(value = "/activateUser/{id}")
	public String activateUser(@PathVariable Long id) {
		System.out.println("Activating User :: " + id);
		appUserRepository.setAppUserAsActiveById(id);
		return "OK!";
	}
	
	

	
}
