package com.contentplusplus.springboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages.properties")
public class AppErrorController implements ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "500";
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "404";
			} else {
				return "error";
			}
		}

		return "error";
	}

	@GetMapping("/403")
	String notFoundPage(Model model) {
		model.addAttribute("pagename", "403");
		return "403";
	}

}
