package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.Circle;

public interface CircleDAO {

	boolean addCircle(Circle circle);
	boolean addUserToCircle(String emailId,String circleName);
	List<Circle> getAllCircles();
	List<Circle> getCircleByUser(String createdBy);
}
