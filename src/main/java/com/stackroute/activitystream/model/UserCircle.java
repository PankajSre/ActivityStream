package com.stackroute.activitystream.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Component
@Table(name="usercircle")
public class UserCircle extends ResourceSupport implements Serializable{

	private static final long serialVersionUID = -1147732076765756355L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int circleId;
	private String circleName;
	private String subscriberId;
	private boolean status;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date dateOfJoining=new Date();
	
	public UserCircle() {
	}
     
	public UserCircle(int circleId, String circleName, String subscriberId, boolean status, Date dateOfJoining) {
		super();
		this.circleId = circleId;
		this.circleName = circleName;
		this.subscriberId = subscriberId;
		this.status = status;
		this.dateOfJoining = dateOfJoining;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

}
