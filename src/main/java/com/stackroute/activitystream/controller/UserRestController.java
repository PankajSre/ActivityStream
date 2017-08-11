package com.stackroute.activitystream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.User;
import com.stackroute.activitystream.viewobject.UserHomeVO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private User user;
   
      @Autowired
      RestTemplate restTemplate;
      public static final String REST_SERVICE_URI = "http://localhost:8888/activityStream/api";
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {

		if (userDAO.saveUser(user) == true) {
			user.setStatusCode("200");
			user.setStatusMessage("you are Successfully registered with " + user.getUsername());
		} else {
			user.setStatusCode("404");
			user.setStatusMessage("Youa are not registered Successfully");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User loginUser(@RequestBody User user, HttpSession session) {
		User loginUser=userDAO.getUserByEmailId(user.getEmailId());
		if(userDAO.validateUser(user.getEmailId(),user.getPassword())){
     
			user.setStatusCode("200");
			user.setStatusMessage("You have successfully logged in.");
			session.setAttribute("loggedInUser", user.getEmailId());
		}
		
		return loginUser;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {
		String username=(String)session.getAttribute("loggedInUser");
		if(username !=null)
		{
		session.invalidate();
		session.setMaxInactiveInterval(0);
		user.setStatusCode("200");
		user.setStatusMessage("You have successfully logged out");
		}
		else
		{
			user.setStatusCode("405");
			user.setStatusMessage("You are not loggedIn ...Please Login");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userById", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setStatusCode("404");
			user.setStatusMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@RequestMapping(value = "/deleteUserById", method = RequestMethod.POST)
	public ResponseEntity<User> deleteUserById(HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setStatusCode("404");
			user.setStatusMessage("User does not exist with this username :"+emailId);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		userDAO.deleteUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@PostMapping(value = "/updateUser")
	public ResponseEntity<?> updateUserById(@RequestBody User updatedUser,HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setStatusCode("404");
			user.setStatusMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		}
		userDAO.updateUser(updatedUser);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		
		List<User> users = userDAO.getAllUsers();
		if (users.isEmpty()) {
			user.setStatusCode("404");
			user.setStatusMessage("There is No user to show....");
			users.add(user);
		}
		for(User user : users)
		{
			Link selfLink = linkTo(UserRestController.class).slash(user.getEmailId()).withSelfRel();
	        user.add(selfLink);
		}
		return users;
	}
}
