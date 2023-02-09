package com.contentplusplus.springboot.model;

import com.contentplusplus.springboot.model.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppCaseDocument")
@Table(name = "app_case_document")
public class AppCaseDocument extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fileuuid", unique = true)
	private String fileuuid;

	@PrePersist
	protected void onCreate() {
		setFileuuid(java.util.UUID.randomUUID().toString());
	}

	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "documenttype")
	private String documenttype;

	@Column(name = "filetype")
	private String fileType;

	@Column(name = "filesize")
	private long fileSize;

	@Lob
	@Column(name = "filedata")
	private byte[] fileData;

	@Version
	@Column(name = "optlock")
	private Integer version;

	public AppCaseDocument(String fileName, String fileType, long fileSize, byte[] fileData) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileData = fileData;
	}
	
	public AppCaseDocument(String fileName, String documenttype, String fileType, long fileSize,
			byte[] fileData, AppCase appCase) {
		super();
		this.fileName = fileName;
		this.documenttype = documenttype;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.appCase = appCase;
	}

	@JsonIgnore
	@JoinColumn(name = "caseid", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private AppCase appCase;

}
