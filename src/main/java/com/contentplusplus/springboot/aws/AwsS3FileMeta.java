package com.contentplusplus.springboot.aws;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "aws_file_meta")
@NoArgsConstructor
@Getter
@Setter
public class AwsS3FileMeta {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "awsfilename", nullable = false)
	private String awsfilename;

	@Column(name = "awsfilepath", nullable = false)
	private String awsfilepath;

	@Column(name = "awsfileversion")
	private String awsfileversion;

	public AwsS3FileMeta(String awsfilename, String awsfilepath, String awsfileversion) {
		this.awsfilename = awsfilename;
		this.awsfilepath = awsfilepath;
		this.awsfileversion = awsfileversion;
	}
}
