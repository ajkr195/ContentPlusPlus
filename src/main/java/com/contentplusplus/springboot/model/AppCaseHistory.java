package com.contentplusplus.springboot.model;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppCaseHistory")
@Table(name = "app_case_history")
public class AppCaseHistory extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "casehistorytext")
	private String casehistorytext;

	@JoinColumn(name = "caseid", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private AppCase appCase;

	public AppCaseHistory(String casehistorytext, AppCase appCase) {
		super();
		this.casehistorytext = casehistorytext;
		this.appCase = appCase;
	}

	public AppCaseHistory(String casehistorytext) {
		super();
		this.casehistorytext = casehistorytext;
	}
	
	
	
	

}
