package com.contentplusplus.springboot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.contentplusplus.springboot.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity(name = "AppCase")
@Table(name = "app_case")
public class AppCase extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "casetitle")
	private String casetitle;

	@Column(name = "currentstepname")
	private String currentstepname;

	@Column(name = "caseuuid", updatable = false)
	private String caseuuid;

	@PrePersist
	protected void onCreate() {
		setCaseuuid(java.util.UUID.randomUUID().toString());
	}

	public AppCase(AppCaseType appCaseType) {
		// TODO Auto-generated constructor stub
	}

	@Column(name = "assignedto")
	private String assignedto;

	@Column(name = "lockedby", nullable = true)
	private String lockedby;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appCase")
	private List<AppCaseDocument> caseDocumentList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appCase")
	private List<AppCaseComment> caseCommentList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "appCase")
	private List<AppCaseHistory> appCaseHistoryList;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "casetypeid", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AppCaseType appCaseType;

	// @OneToMany(cascade = CascadeType.MERGE) //
//	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE  })
//	@JoinColumn(name = "caseid")
//	private List<AppCaseProperty> appCasePropertyList = new ArrayList<>();
	
	@OneToMany( cascade = CascadeType.ALL)
	@JoinColumn(name = "caseid")
	private List<AppCaseProperty> appCasePropertyList = new ArrayList<>();

	@Enumerated(value = EnumType.STRING)
	@Column(name = "casestatus")
	private AppCaseStatus casestatus;
}
