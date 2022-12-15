package com.contentplusplus.springboot.model;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "app_fs_content")
@Table(name = "app_fs_content")
public class AppFSContent extends Auditable<String>  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "filename")
	private String filename;

	@Column(name = "fileurl")
	private String fileurl;
	
	@Column(name = "filesize")
	private long filesize;
	
	@Column(name = "filetype")
	private String filetype;

	@Version
	@Column(name="optlock")
    private Integer version;


	public AppFSContent(String filename, String fileurl, String filetype, long filesize ) {
		super();
		this.filename = filename;
		this.fileurl = fileurl;
		this.filesize = filesize;
		this.filetype = filetype;
	}
	
	public AppFSContent(String filename, String fileurl) {
		super();
		this.filename = filename;
		this.fileurl = fileurl;
	}
	

}
