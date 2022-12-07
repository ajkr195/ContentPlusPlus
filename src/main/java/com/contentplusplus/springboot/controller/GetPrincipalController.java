package com.contentplusplus.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GetPrincipalController {

	@Value("${app.name}")
	private String appname;
	
	@ModelAttribute("principal")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@ModelAttribute("appname")
	public String globalAppName() {
		return appname;
	}
	
}