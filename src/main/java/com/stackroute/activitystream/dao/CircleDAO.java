package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.Circle;

public interface CircleDAO {

	boolean addCircle(Circle circle);
	//this method should not present here. It should be in UserCircleDAO
	boolean addUserToCircle(String emailId,String circleName);
	List<Circle> getAllCircles();
	//this method should not present here. emailId not there in Circle Table so you can't fetch based on emailId
	List<Circle> getCircleByUser(String emailId);
}
