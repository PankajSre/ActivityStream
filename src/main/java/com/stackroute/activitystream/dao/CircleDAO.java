package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.Circle;

public interface CircleDAO {

	boolean addCircle(Circle circle);
	boolean addUserToCircle(String emailId,String circleName);
	List<Circle> getAllCircles();
	boolean deleteCircle(String circleName,String ownerId);
	boolean updateCircle(Circle circle);
	public Circle getCircleByName(String circleName);
	public boolean isCircleExists(String circleName);
}
