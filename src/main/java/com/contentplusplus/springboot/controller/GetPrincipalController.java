package com.contentplusplus.springboot.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class GetPrincipalController {

	@ModelAttribute("principal")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}