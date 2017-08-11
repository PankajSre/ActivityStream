package com.stackroute.activitystream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.model.UserCircle;

@RestController
@RequestMapping("/api/circle")
public class UserCircleRestController {

	@Autowired
	private UserCircleDAO userCircleDAO;
	
	@Autowired
	private UserCircle userCircle;
	
	@PostMapping(value = "/create-user-circle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserCircle> createCircle(@RequestBody UserCircle userCircle) {

		
		if (userCircleDAO.addCircle(userCircle) == true) {
			userCircle.setErrorCode("200");
			userCircle.setErrorMessage("you have Successfully created the Circle ");
		} else {
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Your Circle has not been created");
		}
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}

	@GetMapping(value = "/circle-by-name/{circleName}")
	public ResponseEntity<UserCircle> getCircleById(@PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			userCircle = new UserCircle();
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Circle does not exist");
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		}

		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}

	@PostMapping("/add-user-to-circle/{circleName}")
	public UserCircle addUserToCircle(@RequestBody String emailId, @PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircleDAO.addCircle(userCircle) == true) {
			userCircle.setErrorCode("200");
			userCircle.setErrorMessage("User " + emailId + " is added Successfully to the Circle " + circleName);
		} else {
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("The Circle " + circleName + "does not Exists");
		}
		return userCircle;
	}

	@PutMapping(value = "/deactivate-circle/{circleName}")
	public ResponseEntity<UserCircle> deleteCircleById(@PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			userCircle = new UserCircle();
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Circle does not exist");
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		}
		userCircleDAO.deleteCircle(userCircle);
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}
}
