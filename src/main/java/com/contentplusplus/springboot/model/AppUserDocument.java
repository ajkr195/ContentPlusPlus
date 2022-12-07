package com.contentplusplus.springboot.model;

import org.hibernate.annotations.GenericGenerator;

import com.contentplusplus.springboot.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AppDBFiles")
@Table(name = "app_db_files")
public class AppUserDocument extends Auditable<String>  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "dbfileuuid", unique = true)
	@GenericGenerator(name = "uuid", strategy = "uuid4")
	private String dbfileuuid;

	@Column(name = "dbfilename")
	private String dbfileName;

	@Column(name = "dbfiletype")
	private String dbfileType;
	
	@Column(name = "dbfilesize")
	private long dbfileSize;

	@Lob
	@Column(name = "dbfiledata")
	private byte[] dbfileData;
	
	@Version
	@Column(name="optlock")
    private Integer version;


	public AppUserDocument(String dbfileName, String dbfileType, long dbfileSize, byte[] dbfileData) {
		super();
		this.dbfileName = dbfileName;
		this.dbfileType = dbfileType;
		this.dbfileSize = dbfileSize;
		this.dbfileData = dbfileData;
	}
	
	public AppUserDocument(String dbfileuuid, String dbfileName, String dbfileType, long dbfileSize, byte[] dbfileData) {
		super();
		this.dbfileName = dbfileName;
		this.dbfileType = dbfileType;
		this.dbfileSize = dbfileSize;
		this.dbfileData = dbfileData;
	}

}
