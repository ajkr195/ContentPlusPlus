package com.contentplusplus.springboot.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppDepartment")
@Table(name = "app_department")
public class AppDepartment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "departmentuuid", unique = true)
	private String departmentuuid;
	@PrePersist
	protected void onCreate() {
		setDepartmentuuid(java.util.UUID.randomUUID().toString());
	}
	@Basic(optional = false)
	@Column(name = "departmentname")
	private String departmentname;
	@Basic(optional = false)
	@Column(name = "departmentheadname")
	private String departmentheadname;
	@Basic(optional = false)
	@Column(name = "departmentheademail")
	private String departmentheademail;

}
