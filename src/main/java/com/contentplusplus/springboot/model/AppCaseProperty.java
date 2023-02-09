package com.contentplusplus.springboot.model;

import java.io.Serializable;
import java.util.Date;

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


@Entity
@Table(name = "app_case_property")
public class AppCaseProperty  extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@Column(name = "casepropertyname")
	private String casepropertyname;
	
	@Column(name = "casepropertyvalue")
	private String casepropertyvalue;
	
	@Column(name = "casepropertytype")
	private String casepropertytype;
	
	@Column(name = "casepropertyrequired")
	private String casepropertyrequired;
	
	@Column(name = "casepropertysize")
	private String casepropertysize;
	
	@Column(name = "casepropertymin")
	private String casepropertymin;
	
	@Column(name = "casepropertymax")
	private String casepropertymax;
	
	@Column(name = "casepropertymaxlength")
	private String casepropertymaxlength;
	
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "caseid", nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private AppCase appCase;
	
	
	public AppCaseProperty() {
		super();
	}
	
	public AppCaseProperty(Long id, String casepropertyname, String casepropertyvalue, String casepropertytype,
			String casepropertyrequired, String casepropertysize, String casepropertymin, String casepropertymax,
			String casepropertymaxlength) {
		super();
		this.id = id;
		this.casepropertyname = casepropertyname;
		this.casepropertyvalue = casepropertyvalue;
		this.casepropertytype = casepropertytype;
		this.casepropertyrequired = casepropertyrequired;
		this.casepropertysize = casepropertysize;
		this.casepropertymin = casepropertymin;
		this.casepropertymax = casepropertymax;
		this.casepropertymaxlength = casepropertymaxlength;
	}
	
	public AppCaseProperty(String casepropertyname, String casepropertyvalue, String casepropertytype,
			String casepropertyrequired, String casepropertysize, String casepropertymin, String casepropertymax,
			String casepropertymaxlength) {
		super();
		this.casepropertyname = casepropertyname;
		this.casepropertyvalue = casepropertyvalue;
		this.casepropertytype = casepropertytype;
		this.casepropertyrequired = casepropertyrequired;
		this.casepropertysize = casepropertysize;
		this.casepropertymin = casepropertymin;
		this.casepropertymax = casepropertymax;
		this.casepropertymaxlength = casepropertymaxlength;
	}
	public AppCaseProperty(String casetypepropertyname, String casetypepropertyvalue, String casetypepropertytype,
			String casetypepropertyrequired, String casetypepropertysize, String casetypepropertymin,
			String casetypepropertymax, String casetypepropertymaxlength, String createdBy, Date createdDate,
			String lastModifiedBy, Date lastModifiedDate) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCasepropertyname() {
		return casepropertyname;
	}
	public void setCasepropertyname(String casepropertyname) {
		this.casepropertyname = casepropertyname;
	}
	public String getCasepropertyvalue() {
		return casepropertyvalue;
	}
	public void setCasepropertyvalue(String casepropertyvalue) {
		this.casepropertyvalue = casepropertyvalue;
	}
	public String getCasepropertytype() {
		return casepropertytype;
	}
	public void setCasepropertytype(String casepropertytype) {
		this.casepropertytype = casepropertytype;
	}
	public String getCasepropertyrequired() {
		return casepropertyrequired;
	}
	public void setCasepropertyrequired(String casepropertyrequired) {
		this.casepropertyrequired = casepropertyrequired;
	}
	public String getCasepropertysize() {
		return casepropertysize;
	}
	public void setCasepropertysize(String casepropertysize) {
		this.casepropertysize = casepropertysize;
	}
	public String getCasepropertymin() {
		return casepropertymin;
	}
	public void setCasepropertymin(String casepropertymin) {
		this.casepropertymin = casepropertymin;
	}
	public String getCasepropertymax() {
		return casepropertymax;
	}
	public void setCasepropertymax(String casepropertymax) {
		this.casepropertymax = casepropertymax;
	}
	public String getCasepropertymaxlength() {
		return casepropertymaxlength;
	}
	public void setCasepropertymaxlength(String casepropertymaxlength) {
		this.casepropertymaxlength = casepropertymaxlength;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppCaseProperty other = (AppCaseProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppCaseProperty [id=" + id + ", casepropertyname=" + casepropertyname + ", casepropertyvalue="
				+ casepropertyvalue + ", casepropertytype=" + casepropertytype + ", casepropertyrequired="
				+ casepropertyrequired + ", casepropertysize=" + casepropertysize + ", casepropertymin="
				+ casepropertymin + ", casepropertymax=" + casepropertymax + ", casepropertymaxlength="
				+ casepropertymaxlength + "]";
	}
	
	
	
}
