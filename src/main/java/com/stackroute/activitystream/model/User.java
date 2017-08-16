package com.stackroute.activitystream.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
public class User extends StatusCode implements Serializable {

	private static final long serialVersionUID = -3917567546587261536L;
	
	@NotNull
	private String username;
	@NotEmpty
	@JsonIgnore
	private String password;
	@Id
	@Email
	private String emailId;
	//no validation for mobile number?
	private long mobileNumber;
    private boolean isActive;
    private String role="ROLE_USER";
    public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String emailId, long mobileNumber, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.isActive = isActive;
	}
   
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
