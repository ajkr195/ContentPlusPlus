package com.contentplusplus.springboot.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.contentplusplus.springboot.exception.ResourceNotFoundException;
import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseComment;
import com.contentplusplus.springboot.model.AppCaseHistory;
import com.contentplusplus.springboot.model.AppCasePropertyDto;
import com.contentplusplus.springboot.model.AppCaseStatus;
import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppCaseTypeStep;
import com.contentplusplus.springboot.model.AppDepartment;
import com.contentplusplus.springboot.repository.AppCaseCommentRepository;
import com.contentplusplus.springboot.repository.AppCaseHistoryRepository;
import com.contentplusplus.springboot.repository.AppCasePropertyRepository;
import com.contentplusplus.springboot.repository.AppCaseRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;
import com.contentplusplus.springboot.repository.AppDepartmentRepository;
import com.contentplusplus.springboot.repository.AppRoleRepository;
import com.contentplusplus.springboot.repository.AppUserRepository;
import com.contentplusplus.springboot.service.AppCaseService;
import com.contentplusplus.springboot.validator.AppCaseAddValidator;
import com.contentplusplus.springboot.validator.AppCaseEditValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AppCaseController {

	@Autowired
	AppCaseRepository appCaseRepository;

	@Autowired
	AppCasePropertyRepository appCasePropertyRepository;

	@Autowired
	AppCaseAddValidator appCaseAddValidator;

	@Autowired
	AppCaseEditValidator appCaseEditValidator;

	@Autowired
	AppDepartmentRepository appDepartmentRepository;

	@Autowired
	AppCaseTypeRepository appCaseTypeRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AppCaseService appCaseService;

	@Autowired
	AppCaseCommentRepository appCaseCommentRepository;

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;

	@Autowired
	AppCaseHistoryRepository appCaseHistoryRepository;

	@GetMapping("/caseEditById/{id}")
	public String viewCase(@PathVariable(name = "id") Long id,
			@RequestParam(required = false, name = "assignedto") String assignedto, Model model,
			final RedirectAttributes redirectAttributes) {

		AppCase appCase = appCaseRepository.findById(id).get(); // AppCasePropertyDto

		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Viewed this Case", appCase));

		model.addAttribute("editingappCase", "editingappCase");
		model.addAttribute("pagename", "processcase");
		model.addAttribute("appCase", appCase);

		if (appCase.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE")) {
			model.addAttribute("CLOSED_COMPLETE", "CLOSED_COMPLETE");
		}

//		System.out.println("Current Percentage: "
//				+ getPercentageCompletion(appCase.getCurrentstepname(), appCase.getAppCaseType().getAppCaseStepList()));

		model.addAttribute("completionPercentage",
				getPercentageCompletion(appCase.getCurrentstepname(), appCase.getAppCaseType().getAppCaseStepList()));
		model.addAttribute("appCaseCurrentStep", appCase.getCurrentstepname());

		TreeSet<Long> set = appCaseTypeStepRepository.getStepIdsByCaseType(appCase.getAppCaseType());

		if ((appCase.getCurrentstepname().equalsIgnoreCase("LAUNCHED"))
				|| (appCase.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE"))) {

			if (appCase.getCurrentstepname()
					.equalsIgnoreCase(appCaseTypeStepRepository.findById(set.first()).get().getCasetypestepname())) {
				System.out.println("grrrrrrr...");
				model.addAttribute("FIRSTSTEP", "FIRSTSTEP");
			}

			model.addAttribute("FIRSTSTEP", "FIRSTSTEP");

		}

//		if (!((getnextprev(set, id, "checkifatfirststep", appCase)).equalsIgnoreCase("LAUNCHED"))
//				|| !((getnextprev(set, id, "checkifatfirststep", appCase)).equalsIgnoreCase("FIRST"))) {
//System.out.println("FIRSTSTEP");
//			model.addAttribute("FIRSTSTEP", "FIRSTSTEP");
//		} else {
//			System.out.println("NOT_FIRST_STEP");
//		}

		model.addAttribute("caseId", appCase.getCaseuuid());

//		System.out.println("AppCaseTypeSteps for this Case: "
//				+ appCaseTypeStepRepository.getStepIdsByCaseType(appCase.getAppCaseType()));

		AppCasePropertyDto appCasePropertyDto = new AppCasePropertyDto();
		appCasePropertyDto.setAppCaseProperty(appCasePropertyRepository.getCasePropertiesByCaseId(id));
		model.addAttribute("appCasePropertyDto", appCasePropertyDto);

		return "appcase_edit";

	}

	@PostMapping("/caseEditById")
	public String edit(@ModelAttribute("appCase") @Valid AppCase appCase, BindingResult bindingResult,
			RedirectAttributes redirAttrs, @RequestParam("casecomment") String casecomment, Model model) {

		model.addAttribute("appCase", appCase);

		model.addAttribute("appCaseStatus", appCase.getCasestatus());

		AppCaseType appCaseType = appCaseTypeRepository.findById(appCaseRepository.getCaseTypeId(appCase.getId()))
				.orElse(null); // appCase.getAppCasetype();

		model.addAttribute("caseId", appCase.getId());

		model.addAttribute("caseType", appCaseType.getCasetypename());

		if (bindingResult.hasErrors()) {

			redirAttrs.addFlashAttribute("errorMessage", "The submitted data has errors.");
			return "appcase_edit";

		} else {
			AppCasePropertyDto appCasePropertyDto = new AppCasePropertyDto();
			appCasePropertyDto.setAppCaseProperty(appCasePropertyRepository.getCasePropertiesByCaseId(appCase.getId()));
			model.addAttribute("appCasePropertyDto", appCasePropertyDto);
		}

		AppCaseComment caseComment = new AppCaseComment();

		caseComment.setCasecomment("<b>[" + new Date() + "] " + getPrincipal() + " :</b> " + casecomment);
		caseComment.setAppCase(appCase);
		appCaseCommentRepository.save(caseComment);

		appCaseService.updateCase(appCase);

		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Updated this Case", appCase));

		return "redirect:/caseEditById/" + appCase.getId();
	}

	public String getnextprev(TreeSet<Long> set, Long input, String getNextOrPrev, AppCase appCase) {

		if (!set.contains(input)) {
			// System.out.println("....Invalid input....");
			return "INVALID";
		}
		if (input.equals(set.first())) {
			System.out.println(input + " - is MIN");
			return "FIRST";
		} else if (input.equals(set.last())) {
			// System.out.println(input + " - is MAX");
			return "LAST";
		} else {

			if (getNextOrPrev.equalsIgnoreCase("getnext")) {
				System.out.println("Next  - " + set.tailSet(input, false).first());
				return appCaseTypeStepRepository.findById(input).get().getCasetypestepname();
			} else if (getNextOrPrev.equalsIgnoreCase("getprevious")) {
				System.out.println("Previous - " + set.headSet(input).last());
				return appCaseTypeStepRepository.findById(input).get().getCasetypestepname();
			}

		}
		return null;
	}

	@RequestMapping(value = "/approveCase", method = RequestMethod.POST, params = "action=approve")
	public String approve(@ModelAttribute("appCase") @Valid AppCase appCase, BindingResult bindingResult,
			RedirectAttributes redirAttrs, @RequestParam("casecomment") String casecomment, Model model) {

		model.addAttribute("appCase", appCase);

		model.addAttribute("appCaseStatus", appCase.getCasestatus());

		AppCaseType appCaseType = appCaseTypeRepository.findById(appCaseRepository.getCaseTypeId(appCase.getId()))
				.orElse(null); // appCase.getAppCasetype();

		model.addAttribute("caseId", appCase.getId());

		model.addAttribute("caseType", appCaseType.getCasetypename());

		if (bindingResult.hasErrors()) {

			redirAttrs.addFlashAttribute("errorMessage", "The submitted data has errors.");
			return "appcase_edit";

		} else {
			AppCasePropertyDto appCasePropertyDto = new AppCasePropertyDto();
			appCasePropertyDto.setAppCaseProperty(appCasePropertyRepository.getCasePropertiesByCaseId(appCase.getId()));
			model.addAttribute("appCasePropertyDto", appCasePropertyDto);
		}

		AppCaseComment caseComment = new AppCaseComment();

		caseComment.setCasecomment("<b>[" + new Date() + "] " + getPrincipal() + " :</b> " + casecomment);
		caseComment.setAppCase(appCase);
		appCaseCommentRepository.save(caseComment);

		appCaseService.approveCase(appCase);
		return "redirect:/caseEditById/" + appCase.getId();
	}

	@RequestMapping(value = "/rejectCase", method = RequestMethod.POST, params = "action=reject")
	public String reject(@ModelAttribute("appCase") @Valid AppCase appCase, BindingResult bindingResult,
			RedirectAttributes redirAttrs, @RequestParam("casecomment") String casecomment, Model model) {

		model.addAttribute("appCase", appCase);

		model.addAttribute("appCaseStatus", appCase.getCasestatus());

		AppCaseType appCaseType = appCaseTypeRepository.findById(appCaseRepository.getCaseTypeId(appCase.getId()))
				.orElse(null); // appCase.getAppCasetype();

		model.addAttribute("caseId", appCase.getId());

		model.addAttribute("caseType", appCaseType.getCasetypename());

		if (bindingResult.hasErrors()) {

			redirAttrs.addFlashAttribute("errorMessage", "The submitted data has errors.");
			return "appcase_edit";

		} else {
			AppCasePropertyDto appCasePropertyDto = new AppCasePropertyDto();
			appCasePropertyDto.setAppCaseProperty(appCasePropertyRepository.getCasePropertiesByCaseId(appCase.getId()));
			model.addAttribute("appCasePropertyDto", appCasePropertyDto);
		}

		AppCaseComment caseComment = new AppCaseComment();

		caseComment.setCasecomment("<b>[" + new Date() + "] " + getPrincipal() + " :</b> " + casecomment);
		caseComment.setAppCase(appCase);
		appCaseCommentRepository.save(caseComment);

		appCaseService.rejectCase(appCase);
		return "redirect:/caseEditById/" + appCase.getId();
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@RequestMapping(value = { "/caseProcess/{id}" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(Model model, @PathVariable(required = false, name = "id") Long id) {
		String editingappCase = "editingappCase";
		model.addAttribute("pagename", "processcase");
		model.addAttribute("editingappCase", editingappCase);
		model.addAttribute("appCase", appCaseRepository.findById(id));
		return "appcase_edit";
	}

	@RequestMapping(value = "/caseProcess", method = RequestMethod.POST)
	public String adminusereditRegistration(@Valid @ModelAttribute("department") AppCase appCase,
			BindingResult bindingResult, HttpServletRequest request, Model model) {

		// log.info("User ID is :: " + user.getId());
		model.addAttribute("pagename", "processcase");
		String editingappCase = "editingappCase";
		model.addAttribute("editingappCase", editingappCase);
		model.addAttribute("appCase", appCase);
		appCaseEditValidator.validate(appCase, bindingResult);
		if (bindingResult.hasErrors()) {
			return "appcase_edit";
		}
		appCaseService.updateCase(appCase);
		return "redirect:/listappcase";
	}

	@GetMapping("/listappcase")
	public String documentsworkflowPagenew(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "") String caseStatus,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			model.addAttribute("pagename", "listappcase");
			List<AppCase> allfiles = new ArrayList<>();

			String sortField = sort[0];
			String sortDirection = sort[1];

			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			Order order = new Order(direction, sortField);

			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

			Page<AppCase> pageTuts = null;

			if (keyword != null) {
				model.addAttribute("keyword", keyword);
			}

			if (caseStatus != "") {
				model.addAttribute("caseStatus", caseStatus);
			}

			System.out.println("keyword: " + keyword + " caseStatus: " + caseStatus);

			if (keyword == null && caseStatus == "") {
				log.info("Inside keyword == null caseStatus== null");
				pageTuts = appCaseRepository.findAll(pageable);
			} else if (keyword == "" && caseStatus == "") {
				log.info("Inside keyword == AND caseStatus== condition");
				pageTuts = appCaseRepository.findAll(pageable);
			} else if (keyword != null && caseStatus.equalsIgnoreCase("NEW")) {
				log.info("Inside keyword != null AND caseStatus== NEW condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.NEW,
						keyword, pageable);
			} else if (keyword != null && caseStatus.equalsIgnoreCase("IN_PROGRESS")) {
				log.info("Inside keyword != null AND  caseStatus== IN_PROGRESS condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.IN_PROGRESS,
						keyword, pageable);
			} else if (keyword != null && caseStatus.equalsIgnoreCase("CLOSED")) {
				log.info("Inside keyword != null AND  caseStatus== CLOSED condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.CLOSED,
						keyword, pageable);
			} else if (keyword == null && caseStatus.equalsIgnoreCase("CLOSED")) {
				log.info("Inside keyword == null AND  caseStatus== CLOSED condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.CLOSED,
						keyword, pageable);
			} else if (keyword == null && caseStatus.equalsIgnoreCase("NEW")) {
				log.info("Inside keyword == null AND  caseStatus== NEW condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.NEW,
						keyword, pageable);
			} else if (keyword == null && caseStatus.equalsIgnoreCase("IN_PROGRESS")) {
				log.info("Inside keyword == null AND  caseStatus== IN_PROGRESS condition");
				pageTuts = appCaseRepository.findByCasestatusAndCasetitleContainingIgnoreCase(AppCaseStatus.CLOSED,
						keyword, pageable);
			} else if (keyword == null && caseStatus.equalsIgnoreCase("ALL")) {
				log.info("Inside keyword == null AND  caseStatus== CLOSED condition");
				pageTuts = appCaseRepository.findAll(pageable);
			} else if (keyword != "" && keyword != null) {
				log.info("Inside keyword != null condition");
				pageTuts = appCaseRepository.findByCasetitleContainingIgnoreCase(keyword, pageable);
			} else {
				log.info("Inside findall condition");
				pageTuts = appCaseRepository.findAll(pageable);
			}

			allfiles = pageTuts.getContent();

			model.addAttribute("allfiles", allfiles);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("totalfiles", appCaseRepository.count());
			model.addAttribute("pageSize", size);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDirection", sortDirection);
			model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "appcase_list";
	}

	@GetMapping("/appCasenew/{caseTypeid}")
	public String appCaseNew(@PathVariable(required = false, name = "caseTypeid") Long caseTypeId,
			HttpServletRequest request, Model model) {
		String creatingappCase = "creatingappCase";
		model.addAttribute("creatingappCase", creatingappCase);
		model.addAttribute("appCase", new AppCase());
		model.addAttribute("appCaseType", appCaseTypeRepository.findById(caseTypeId));

		List<AppDepartment> deptList = new ArrayList<>();
		Principal principal = request.getUserPrincipal();
		for (AppDepartment dept : appDepartmentRepository.findByUsers_Useremail(principal.getName())) {
			deptList.add(dept);
		}
		model.addAttribute("eligiblecasetypes", appCaseTypeRepository.findByAppDepartmentIn(deptList));

		return "appcase_new";
	}

	@PostMapping("/appCasenew")
	public String appCaseNew(@Valid @ModelAttribute("appCase") AppCase appCase, BindingResult bindingResult,
			HttpServletRequest request, Model model) {

		List<AppDepartment> deptList = new ArrayList<>();
		Principal principal = request.getUserPrincipal();
		for (AppDepartment dept : appDepartmentRepository.findByUsers_Useremail(principal.getName())) {
			deptList.add(dept);
		}
		model.addAttribute("eligiblecasetypes", appCaseTypeRepository.findByAppDepartmentIn(deptList));

		model.addAttribute("appCase", appCase);
		String creatingappCase = "creatingappCase";
		model.addAttribute("creatingappCase", creatingappCase);
		appCaseAddValidator.validate(appCase, bindingResult);
		if (bindingResult.hasErrors()) {
			return "appcase_new";
		}
		appCaseService.save(appCase);
		return "redirect:/listappcase";
	}

	@RequestMapping(value = { "/launchpad" }, method = RequestMethod.GET)
	public String adminusereditRegistrationsd(HttpServletRequest request, Model model) {

		List<AppDepartment> deptList = new ArrayList<>();

		Principal principal = request.getUserPrincipal();

		for (AppDepartment dept : appDepartmentRepository.findByUsers_Useremail(principal.getName())) {
			deptList.add(dept);
		}
		// System.out.println("SecondList:"+
		// appCaseTypeRepository.findByDepartmentIn(deptList));
		model.addAttribute("pagename", "launchpad");

		model.addAttribute("eligiblecasetypes", appCaseTypeRepository.findByAppDepartmentIn(deptList));

		return "launchpad";
	}

	@PostMapping(value = "/appCaseCreateByCaseTypeId/{appCaseTypeId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createAppCaseType(@PathVariable(value = "appCaseTypeId") String appCaseTypeId,
			@RequestBody AppCase postdata) {

		//System.out.println("addressRequest : :" + postdata);

		String appCase = appCaseTypeRepository.findById(Long.parseLong(appCaseTypeId)).map(caseType -> {
			postdata.setAppCaseType(caseType);

			appCaseService.save(postdata);

			return "OK";
		}).orElseThrow(() -> new ResourceNotFoundException("Not found for caseType = " + appCaseTypeId));

		return new ResponseEntity<>(appCase, HttpStatus.CREATED);
	}

	@PostMapping(value = "/appCaseApproveByCaseId/{caseId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> forwardCase(@PathVariable(value = "caseId") String caseId) {

		appCaseService.forwardCase(Long.parseLong(caseId));

		AppCase appCase = appCaseRepository.findById(Long.parseLong(caseId)).get();

		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Approved this Case - ", appCase));

		return new ResponseEntity<>(caseId, HttpStatus.CREATED);
	}

	@PostMapping(value = "/appCaseRejectByCaseId/{caseId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> backwardCase(@PathVariable(value = "caseId") String caseId) {

		appCaseService.backwardCase(Long.parseLong(caseId));

		AppCase appCase = appCaseRepository.findById(Long.parseLong(caseId)).get();

		appCaseHistoryRepository.save(new AppCaseHistory(getPrincipal() + " - Rejected this Case - ", appCase));

		return new ResponseEntity<>(caseId, HttpStatus.CREATED);
	}

	public String getPercentageCompletion(String stepname, List<AppCaseTypeStep> strList) {
//		strList = new ArrayList<>();
		List<String> stepNamesList = new ArrayList<>();
		for (AppCaseTypeStep s : strList) {
			stepNamesList.add(s.getCasetypestepname());
//			System.out.println("AppCaseStepName :: " + s.getCasestepname());
		}

		for (String s : stepNamesList) {
//			System.out.println("StepName :: "+ s);
			if (s.equalsIgnoreCase(stepname)) {
//				System.out.println("Found "+ stepname +" at index # " + stepNamesList.indexOf(stepname));
//				System.out.println("Total Size :: " + stepNamesList.size());
//				System.out.println("Completion Percentage :: " + stepNamesList.indexOf(stepname) * 100 / stepNamesList.size() );
				return Integer.toString(stepNamesList.indexOf(stepname) * 100 / stepNamesList.size());
			} else if (s.equalsIgnoreCase("Closed_Complete")) {
				return Integer.toString(100);
			}
		}
		return null;
	}

}
