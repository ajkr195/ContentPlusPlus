package com.contentplusplus.springboot.model;

import java.io.Serializable;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppCaseTask")
@Table(name = "app_case_task")

public class AppCaseTask  extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tasktitle", nullable = false)
	private String tasktitle;
	
	@Column(name = "taskcompleted")
	private boolean taskcompleted;
	
	
	@JoinColumn(name = "caseid", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private AppCase appCase;


}
