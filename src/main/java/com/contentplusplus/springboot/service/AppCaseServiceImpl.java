package com.contentplusplus.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contentplusplus.springboot.model.AppCase;
import com.contentplusplus.springboot.model.AppCaseHistory;
import com.contentplusplus.springboot.model.AppCaseProperty;
import com.contentplusplus.springboot.model.AppCaseStatus;
import com.contentplusplus.springboot.model.AppCaseTypeProperty;
import com.contentplusplus.springboot.repository.AppCaseHistoryRepository;
import com.contentplusplus.springboot.repository.AppCaseRepository;
import com.contentplusplus.springboot.repository.AppCaseTypePropertyRepository;
import com.contentplusplus.springboot.repository.AppCaseTypeStepRepository;

@Service
@Transactional(timeout = 5)
public class AppCaseServiceImpl implements AppCaseService {

	@Autowired
	AppCaseRepository appCaseRepository;

	@Autowired
	AppCaseTypeStepRepository appCaseStepRepository;

	@Autowired
	AppCaseTypePropertyRepository appCaseTypePropertyRepository;

	@Autowired
	AppCaseTypeStepRepository appCaseTypeStepRepository;

	@Autowired
	AppCaseHistoryRepository appCaseHistoryRepository;

	@Override
	public AppCase findByCaseId(Long id) {
		return appCaseRepository.findById(id).orElse(null);
	}

	@Override
	public List<AppCase> findAllCases() {
		return appCaseRepository.findAll();
	}

	@Override
	public void save(AppCase appCase) {
//		appCase.setAppCaseType(appCase.getAppCaseType());

		List<AppCaseTypeProperty> sourceList = appCaseTypePropertyRepository
				.getCaseTypePropertiesByCaseTypeId(appCase.getAppCaseType().getId());

		List<AppCaseProperty> appCaseProperties = sourceList.stream()
				.map(obj -> new AppCaseProperty(obj.getCasetypepropertyname(), obj.getCasetypepropertyvalue(),
						obj.getCasetypepropertytype(), obj.getCasetypepropertyrequired(), obj.getCasetypepropertysize(),
						obj.getCasetypepropertymin(), obj.getCasetypepropertymax(), obj.getCasetypepropertymaxlength()))
				.collect(Collectors.toList());

		appCase.setAppCasePropertyList(appCaseProperties);
		appCase.setAppCaseType(appCase.getAppCaseType());
		appCase.setCasestatus(AppCaseStatus.NEW);
		appCase.setCurrentstepname("LAUNCHED");
		appCaseRepository.save(appCase);
	}

	@Override
	public void updateCase(AppCase appCase) {
		AppCase entity = appCaseRepository.findById(appCase.getId()).orElse(null);
		if (entity != null) {
			entity.setAppCasePropertyList(appCase.getAppCasePropertyList());
			entity.setAppCaseType(entity.getAppCaseType());
			entity.setCasetitle(entity.getCasetitle());

			if (entity.getAssignedto() == null) {
				entity.setAssignedto(appCase.getAssignedto());
			} else {
				entity.setAssignedto(entity.getAssignedto());
			}
			
			if (entity.getLockedby() == null) {
				entity.setLockedby(appCase.getLockedby());
			} else {
				entity.setLockedby(entity.getLockedby());
			}
			
			entity.setCaseuuid(entity.getCaseuuid());
			entity.setCasestatus(AppCaseStatus.IN_PROGRESS);

		}

		List<String> casePropsNew = new ArrayList<>();

		for (AppCaseProperty casePropTheNew : appCase.getAppCasePropertyList()) {
			casePropsNew.add(casePropTheNew.getCasepropertyvalue());
		}

		appCaseHistoryRepository
				.save(new AppCaseHistory(getPrincipal() + " - Updated case properties : " + casePropsNew, appCase));
		appCaseRepository.save(entity);
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

	@Override
	public void forwardCase(Long caseId) {

		AppCase entity = appCaseRepository.findById(caseId).orElse(null);
		String currStep = entity.getCurrentstepname();
		TreeSet<Long> set = appCaseTypeStepRepository.getStepIdsByCaseType(entity.getAppCaseType());

		if (!entity.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE")) {

			appCaseRepository.updateCaseStatus(AppCaseStatus.CLOSED, caseId);

			Long curreStepId;

			if (entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")) {
				//System.out.println("One...");
				appCaseRepository.updateCaseStatus(AppCaseStatus.IN_PROGRESS, caseId);
				currStep = appCaseTypeStepRepository.findById(set.first()).get().getCasetypestepname();
			} else {
				//System.out.println("Two...");
				curreStepId = appCaseTypeStepRepository.findByCasetypestepnameIgnoreCase(currStep).getId();

				if (curreStepId.equals(set.last())) {
//					if (curreStepId.equals(set.last() - 1)) {
					//System.out.println("Three...");
					appCaseRepository.updateCaseStatus(AppCaseStatus.CLOSED, caseId);
					currStep = "CLOSED_COMPLETE";
				} else {
					//System.out.println("Four...");
					appCaseRepository.updateCaseStatus(AppCaseStatus.IN_PROGRESS, caseId);
					currStep = appCaseTypeStepRepository.findById(set.tailSet(curreStepId, false).first()).get()
							.getCasetypestepname();// set.tailSet(input, false).first()
				}
			}

		} else {
			//System.out.println("Five...");
			entity.setCurrentstepname(entity.getCurrentstepname());
		}
		appCaseRepository.updateCurrentStep(currStep, entity.getId());

	}

	@Override
	public void backwardCase(Long caseId) {

		appCaseRepository.updateCaseStatus(AppCaseStatus.IN_PROGRESS, caseId);

		AppCase entity = appCaseRepository.findById(caseId).orElse(null);

		TreeSet<Long> set = appCaseTypeStepRepository.getStepIdsByCaseType(entity.getAppCaseType());

		if (!entity.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE")
				&& !entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")) {

			String currStep = entity.getCurrentstepname();

			Long curreStepId;

			if (entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")) {
				entity.setCurrentstepname(appCaseTypeStepRepository.findById(set.first()).get().getCasetypestepname());

//				System.out.println("one...");

			} else {
				curreStepId = appCaseTypeStepRepository.findByCasetypestepnameIgnoreCase(currStep).getId();

//					entity.setCurrentstepname(appCaseTypeStepRepository
//							.findById(set.tailSet(curreStepId, false).first()).get().getCasetypestepname());
				if (curreStepId == appCaseTypeStepRepository.findById(set.first()).get().getId()) {
					currStep = "LAUNCHED";
//					System.out.println("two...");
				} else {
//					entity.setCurrentstepname(appCaseTypeStepRepository.findById(set.headSet(curreStepId).last()).get()
//							.getCasetypestepname());  //set.headSet(input).last()

					currStep = appCaseTypeStepRepository.findById(set.headSet(curreStepId).last()).get()
							.getCasetypestepname();
//					System.out.println("Setting the step to: " + appCaseTypeStepRepository.findById(set.headSet(curreStepId).last()).get()
//							.getCasetypestepname());
//					System.out.println("three...");

					appCaseRepository.updateCurrentStep(currStep, caseId);
				}
			}
			appCaseRepository.updateCurrentStep(currStep, entity.getId());
		} else {
			entity.setCurrentstepname(entity.getCurrentstepname());
		}

	}

	public String getnextprev(TreeSet<Long> set, Long input, String getNextOrPrev, AppCase appCase) {

		if (!set.contains(input)) {
			// System.out.println("....Invalid input....");
			return "INVALID";
		}
		if (input.equals(set.first())) {
			// System.out.println(input + " - is MIN");
			return "FIRST";
		} else if (input.equals(set.last())) {
			// System.out.println(input + " - is MAX");
			return "LAST";
		} else {

			if (getNextOrPrev.equalsIgnoreCase("getnext")) {
				//System.out.println("Next  - " + set.tailSet(input, false).first());
				return appCaseTypeStepRepository.findById(input).get().getCasetypestepname();
			} else if (getNextOrPrev.equalsIgnoreCase("getprevious")) {
				//System.out.println("Previous - " + set.headSet(input).last());
				return appCaseTypeStepRepository.findById(input).get().getCasetypestepname();
			}

		}
		return null;
	}

	@Override
	public void approveCase(AppCase appCase) {
		AppCase entity = appCaseRepository.findById(appCase.getId()).orElse(null);

		TreeSet<Long> set = appCaseTypeStepRepository.getStepIdsByCaseType(entity.getAppCaseType());

		System.out.println("Case Step ID Set: " + set);

		if (entity != null) {
			entity.setAppCasePropertyList(appCase.getAppCasePropertyList());
			entity.setAppCaseType(entity.getAppCaseType());
			entity.setCasetitle(entity.getCasetitle());
			entity.setAssignedto(appCase.getAssignedto());
			entity.setLockedby(appCase.getLockedby());
			entity.setCaseuuid(entity.getCaseuuid());

			if (!entity.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE")) {
				String currStep = entity.getCurrentstepname();

				Long curreStepId;

				if (entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")) {
					entity.setCurrentstepname(
							appCaseTypeStepRepository.findById(set.first()).get().getCasetypestepname());
				} else {
					curreStepId = appCaseTypeStepRepository.findByCasetypestepnameIgnoreCase(currStep).getId();

					if (curreStepId.equals(set.last())) {
						entity.setCurrentstepname("CLOSED_COMPLETE");
					} else {
						entity.setCurrentstepname(appCaseTypeStepRepository
								.findById(set.tailSet(curreStepId, false).first()).get().getCasetypestepname());
					}
				}
			} else {
				entity.setCurrentstepname(entity.getCurrentstepname());
			}

		}
		appCaseRepository.save(entity);
//		appCaseRepository.setAppCaseCurrentStep();

	}

	@Override
	public void rejectCase(AppCase appCase) {
		AppCase entity = appCaseRepository.findById(appCase.getId()).orElse(null);

		TreeSet<Long> set = appCaseTypeStepRepository.getStepIdsByCaseType(entity.getAppCaseType());

		if (entity != null) {
			entity.setAppCasePropertyList(appCase.getAppCasePropertyList());
			entity.setAppCaseType(entity.getAppCaseType());
			entity.setCasetitle(entity.getCasetitle());
			entity.setAssignedto(appCase.getAssignedto());
			entity.setLockedby(appCase.getLockedby());
			entity.setCaseuuid(entity.getCaseuuid());
			if (!entity.getCurrentstepname().equalsIgnoreCase("CLOSED_COMPLETE")) {
				String currStep = entity.getCurrentstepname();

				Long curreStepId;

				if (entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")
						&& entity.getCurrentstepname().equalsIgnoreCase("LAUNCHED")) {
					entity.setCurrentstepname(
							appCaseTypeStepRepository.findById(set.first()).get().getCasetypestepname());
				} else {
					curreStepId = appCaseTypeStepRepository.findByCasetypestepnameIgnoreCase(currStep).getId();

					if (curreStepId.equals(set.last())) {
						entity.setCurrentstepname("CLOSED_COMPLETE");
					} else {
						entity.setCurrentstepname(appCaseTypeStepRepository.findById(set.headSet(curreStepId).last())
								.get().getCasetypestepname());
					}
				}
			} else {
				entity.setCurrentstepname(entity.getCurrentstepname());
			}
			appCaseRepository.save(entity);

		}
	}

	@Override
	public AppCase saveAppCase(AppCase appCase) {
		return appCaseRepository.save(appCase);

	}

	@Override
	public AppCase saveAppCase2(AppCase appCase) {
		AppCase entity = appCaseRepository.findById(appCase.getId()).orElse(null);
		if (entity != null) {
			appCase.setAssignedto(entity.getAssignedto());
		}
		return appCaseRepository.save(appCase);

	}

	@Override
	public void unlockCaseByCaseUUID(String caseuuid) {
		appCaseRepository.unlockCaseByCaseUUID(caseuuid);
	}

	@Override
	public void clearAssignedTo(Long id) {
		// TODO Auto-generated method stub
		appCaseRepository.clearAssignedTo(id);
	}
	
	@Override
	public void clearLockedBy(Long id) {
		// TODO Auto-generated method stub
		appCaseRepository.clearLockedBy(id);
	}

}
