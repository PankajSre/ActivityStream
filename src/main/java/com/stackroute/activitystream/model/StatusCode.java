package com.stackroute.activitystream.model;

import org.springframework.hateoas.ResourceSupport;

public class StatusCode extends ResourceSupport {

	private String statusCode;
	private String statusMessage;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
    
}
