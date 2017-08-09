package com.stackroute.activitystream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
			user.setErrorCode("200");
			user.setErrorMessage("you are Successfully registered with " + user.getUsername());
		} else {
			user.setErrorCode("404");
			user.setErrorMessage("Youa are not registered Successfully");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserHomeVO loginUser(@RequestBody User user, HttpSession session) {
		boolean userHome = userDAO.validateUser("ram@gmail.com", "ram");
      UserHomeVO userHomeVO=new UserHomeVO();
		  if(userHome)
		  {
			 userHomeVO= getAllDataForUserHome(userHomeVO,user);
		  }
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged in.");
			session.setAttribute("loggedInUser", user.getEmailId());
		
		return userHomeVO;
	}

	private UserHomeVO getAllDataForUserHome(UserHomeVO userHome,User user) {
		
		userHome.setEmailid(user.getEmailId());
		userHome.setUsername(user.getUsername());
		System.out.println(user.getEmailId()+"  "+user.getUsername());
		
		List<Message> userMessages=restTemplate.getForObject(REST_SERVICE_URI+"/message/getMyMessages", List.class);
		List<String> messageByCircle = restTemplate.getForObject(REST_SERVICE_URI+"/message/getAllMessagesByCircleName/12", List.class);
		List<String> userCircles=restTemplate.getForObject(REST_SERVICE_URI+"/circle/circleById/"+user.getEmailId(), List.class);
		userHome.setAllUsers(userDAO.getAllUsers());
		userHome.setMyCircles(userCircles);
		return userHome;
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {
		String username=(String)session.getAttribute("loggedInUser");
		if(username !=null)
		{
		session.invalidate();
		session.setMaxInactiveInterval(0);
		user.setErrorCode("200");
		user.setErrorMessage("You have successfully logged out");
		}
		else
		{
			user.setErrorCode("405");
			user.setErrorMessage("You are not loggedIn ...Please Login");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/userById", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@RequestMapping(value = "/deleteUserById/{username}", method = RequestMethod.POST)
	public ResponseEntity<User> deleteUserById(HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with this username :"+emailId);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		userDAO.deleteUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserById(@RequestBody User updatedUser,HttpSession session) {
		String emailId=(String) session.getAttribute("loggedInUser");
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		}
		userDAO.updateUser(updatedUser);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		
		List<User> users = userDAO.getAllUsers();
		if (users.isEmpty()) {
			user.setErrorCode("404");
			user.setErrorMessage("There is No user to show....");
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
