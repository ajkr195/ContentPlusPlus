package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contentplusplus.springboot.model.AppCaseType;
import com.contentplusplus.springboot.model.AppDepartment;

import jakarta.transaction.Transactional;

public interface AppCaseTypeRepository extends JpaRepository<AppCaseType, Long> {

	List<AppCaseType> findAllByAppDepartmentOrderByIdAsc(AppDepartment appDepartment);

	@Query(value = "SELECT u FROM AppCaseType u where u.appDepartment =:appDepartment")
	List<AppCaseType> findAllAppCaseTypeByAppDepartment(@Param("appDepartment") AppDepartment appDepartment);

	@Query("select new AppCaseType(act.id, act.casetypename, act.casetypedescription, act.casetypeuuid, act.casetypesladuration, act.casetypeslaunit) from AppCaseType act where act.appDepartment =:appDepartment")
	public List<AppCaseType> findByFewFields(@Param("appDepartment") AppDepartment appDepartment);

	AppCaseType findByAppDepartment(Long appDepartmentId);

	AppCaseType findByCasetypenameContainingIgnoreCase(String casetypename);

	AppCaseType findByAppDepartmentAndCasetypenameIgnoreCase(AppDepartment appDepartment, String casetypename);

	@Transactional
	void deleteByAppDepartmentId(long appDepartmentId);

	Page<AppCaseType> findByCasetypenameContainingIgnoreCase(String keyword, Pageable pageable);
	
	@Query("SELECT act FROM AppCaseType act WHERE act.appDepartment in :ids")
	List<AppCaseType> getAppCaseTypeListByDeptIdListIn(@Param("ids") List<Long> deptIdList);

	List<AppCaseType> findByAppDepartmentIn(List<AppDepartment> deptList);
	
	List<AppCaseType> findByAppDepartmentIn(List<AppDepartment> deptList, Pageable pageable);

}
