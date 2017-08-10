package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;

//In this DAO, you should not create/delete circles. 
//Just you should able to subscribe / unsubscribe to circle as well as getAllCircles(String email)
//and getAllUsers(String circleName)

public interface UserCircleDAO {

	//Why this method present in this DAO.  
	boolean addCircle(UserCircle circle);
	boolean addUserToCircle(String emailId,String circleName);
	boolean deleteUserFromCircle(String emailId,String circleName);
	//Why this method present in this DAO.  
	boolean deleteCircle(UserCircle circle);
	//Why this method present in this DAO.  
	List<Circle> getAllCircles();
	//The parameter name should not be createdBy(if you want to get all the circles by user mail id)
	List<UserCircle> getCircleByUser(String createdBy);
	List<String> getUserByCircle(String circleName);
	//By sending circleName, you can't get single UserCircle.  There will be multiple records exist.
	public UserCircle getCircleByName(String circleName);
}
