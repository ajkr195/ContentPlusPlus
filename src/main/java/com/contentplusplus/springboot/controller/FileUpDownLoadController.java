package com.contentplusplus.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contentplusplus.springboot.model.AppUserDocument;
import com.contentplusplus.springboot.repository.AppUserDocumentRepository;

@Controller
public class FileUpDownLoadController {
	
	@Autowired
	AppUserDocumentRepository appUserDocumentRepository;
	
	
	@RequestMapping(value = { "documents" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		List<AppUserDocument> allfiles = appUserDocumentRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "documents";
	}

}
