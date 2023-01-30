package com.contentplusplus.springboot.model;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.contentplusplus.springboot.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity(name = "AppCaseType")
@Table(name = "app_case_type")
public class AppCaseType extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "casetypename", unique = true)
	private String casetypename;

	@Column(name = "casetypeuuid", unique = true)
	private String casetypeuuid;

	@PrePersist
	protected void onCreate() {
		setCasetypeuuid(java.util.UUID.randomUUID().toString());
	}
	
	@Column(name = "casetypedescription")
	private String casetypedescription;
	
	@Column(name = "casetypesladuration")
	private String casetypesladuration;
	
	@Column(name = "casetypeslaunit")
	private String casetypeslaunit;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "departmentid", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AppDepartment appDepartment;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AppCaseTypeProperty> casetypepropertyList;

	public AppCaseType(Long id, String casetypename) {
		super();
		this.id = id;
		this.casetypename = casetypename;
	}

	public AppCaseType(Long id, String casetypename, String casetypeuuid, String casetypedescription,
			String casetypesladuration, String casetypeslaunit) {
		super();
		this.id = id;
		this.casetypename = casetypename;
		this.casetypeuuid = casetypeuuid;
		this.casetypedescription = casetypedescription;
		this.casetypesladuration = casetypesladuration;
		this.casetypeslaunit = casetypeslaunit;
	}

}
