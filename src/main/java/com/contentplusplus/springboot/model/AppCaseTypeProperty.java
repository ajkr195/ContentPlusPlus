package com.contentplusplus.springboot.model;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.contentplusplus.springboot.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppCaseTypeProperty")
@Table(name = "app_case_type_property")
public class AppCaseTypeProperty extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "casetypepropertyname")
	private String casetypepropertyname;
	
	@Column(name = "casetypepropertyvalue")
	private String casetypepropertyvalue;
	
	@Column(name = "casepropertytype")
	private String casetypepropertytype;
	
	@Column(name = "casepropertyrequired")
	private String casetypepropertyrequired;
	
	@Column(name = "casepropertysize")
	private String casetypepropertysize;
	
	@Column(name = "casepropertymin")
	private String casetypepropertymin;
	
	@Column(name = "casepropertymax")
	private String casetypepropertymax;
	
	@Column(name = "casepropertymaxlength")
	private String casetypepropertymaxlength;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "casetypeid", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AppCaseType appCaseType;
	
}
