package com.stackroute.activitystream.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Component
@Table(name="circle")
public class Circle extends StatusCode implements Serializable{

	private static final long serialVersionUID = -1147732076765756355L;
	
	@Id
	private String circleName;
	private String ownerEmailId;
	private boolean status;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date creationDate=new Date();
	
	public Circle() {
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

    
	public String getOwnerEmailId() {
		return ownerEmailId;
	}

	public void setOwnerEmailId(String ownerEmailId) {
		this.ownerEmailId = ownerEmailId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
