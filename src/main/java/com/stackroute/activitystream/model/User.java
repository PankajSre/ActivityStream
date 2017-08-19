package com.stackroute.activitystream.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
public class User extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = -3917567546587261536L;
	
	@NotNull
	private String username;
	@NotEmpty
	private String password;
	@Id
	@Email
	private String emailId;
	private long mobileNumber;
    private boolean isActive;
    public User() {
	
	}
	public User(String username, String password, String emailId, long mobileNumber, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.isActive = isActive;
	}
   
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
