package com.contentplusplus.springboot.model;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppWorkflowDocument")
@Table(name = "app_workflow_document")
public class AppWorkflowDocument extends Auditable<String> {
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "workflowstatus")
    private AppWorkFlowDocumentStatus workflowstatus;

	public AppWorkflowDocument(String fileName, String fileType, long fileSize, byte[] fileData) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileData = fileData;
	}
	
	public AppWorkflowDocument(String fileName, String fileType, long fileSize, byte[] fileData, AppWorkFlowDocumentStatus workflowstatus) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.workflowstatus = workflowstatus;
	}

}
