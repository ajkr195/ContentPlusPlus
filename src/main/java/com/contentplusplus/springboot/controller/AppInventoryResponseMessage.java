package com.contentplusplus.springboot.controller;

public class AppInventoryResponseMessage {

	private String message;
	private String fileDownloadUri;
	

	  public AppInventoryResponseMessage(String message, String fileDownloadUri) {
	    this.message = message;
	    this.fileDownloadUri = fileDownloadUri;
	  }


	public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	  
}
