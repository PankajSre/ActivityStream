package com.stackroute.activitystream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.model.User;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private User user;

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {

		if (userDAO.saveUser(user) == true) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user, HttpSession session) {
		User loginUser = userDAO.getUserByEmailId(user.getEmailId());
		if (userDAO.validateUser(user.getEmailId(), user.getPassword())) {
			session.setAttribute("loggedInUser", user.getEmailId());
			return new ResponseEntity<User>(loginUser, HttpStatus.OK);
		}
         
		return new ResponseEntity<User>(loginUser, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession session) {
		String username = (String) session.getAttribute("loggedInUser");
		if (username != null) {
			session.invalidate();
			session.setMaxInactiveInterval(0);
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/userById/{emailId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("emailId") String emailId) {
		User user = userDAO.getUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteUserById", method = RequestMethod.POST)
	public ResponseEntity<User> deleteUserById(@RequestBody User user) {
		
		if (user == null) {
			user = new User();
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		} else {
			userDAO.deleteUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/updateUser")
	public ResponseEntity<?> updateUserById(@RequestBody User updatedUser, HttpSession session) {
		
		if (updatedUser == null) {
			updatedUser = new User();
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		user.setActive(updatedUser.isActive());
		user.setMobileNumber(updatedUser.getMobileNumber());
		user.setPassword(updatedUser.getPassword());
		user.setUsername(updatedUser.getUsername());
		user.setEmailId(updatedUser.getEmailId());
		userDAO.updateUser(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers() {

		List<User> users = userDAO.getAllUsers();
		if (users.isEmpty()) {
			users.add(user);
		}
		for (User user : users) {
			Link selfLink = linkTo(UserRestController.class).slash(user.getEmailId()).withSelfRel();
			user.add(selfLink);
		}
		return users;
	}
}
