package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.User;

public interface UserDAO {

	public boolean saveUser(User user);
	public User getUserByEmailId(String emailId);
	public boolean deleteUser(User user);
	public boolean updateUser(User user);
	public List<User> getAllUsers();
	public boolean validateUser(String emailId, String password);
	
}
