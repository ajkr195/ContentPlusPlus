package com.contentplusplus.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppDepartment;


@Repository
public interface AppDepartmentRepository extends JpaRepository<AppDepartment, Long> {
	
	AppDepartment findByDepartmentname(String departmentname);
	
	AppDepartment findByDepartmentnameIgnoreCase(String departmentname);

	List<AppDepartment> findByDepartmentnameIgnoreCaseContaining(String departmentname);
	
	@Query( "select apa from AppDepartment apa where id in :ids" )
	List<AppDepartment> getDepartmentsListByIdListIn(@Param("ids") List<Long> departmentIdsList);

	Page<AppDepartment> findByDepartmentnameContainingIgnoreCase(String keyword, Pageable pageable);

	AppDepartment findByDepartmentnameContainingIgnoreCase(String departmentname);

}
