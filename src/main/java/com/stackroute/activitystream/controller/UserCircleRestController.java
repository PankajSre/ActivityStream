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

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;

@RestController
@RequestMapping("/api/circle")
public class UserCircleRestController {

	@Autowired
	private UserCircleDAO userCircleDAO;
	@Autowired
	private CircleDAO circleDAO;

	@Autowired
	private Circle circle;

	//Why to create Circle in this controller?  Circle CRUD operations should be in Circle Controller only
	//In this "UserCircleRestController" you can subsribe/unsubscribe to circle.
	@PostMapping(value = "/createCircle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserCircle> createCircle(@RequestBody UserCircle userCircle) {

		circle.setCircleName(userCircle.getCircleName());
		circle.setOwnerEmailId(userCircle.getOwnerEmailId());
		circle.setCreationDate(userCircle.getCreationDate());
		circle.setStatus(userCircle.isStatus());
		if (userCircleDAO.addCircle(userCircle) == true && circleDAO.addCircle(circle) == true) {
			userCircle.setErrorCode("200");
			userCircle.setErrorMessage("you have Successfully created the Circle ");
		} else {
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Your Circle has not been created");
		}
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}

	//Better to change the method name getCircleByName as you are getting Circle by name not id.
	@GetMapping(value = "/circle-by-name/{circleName}")
	public ResponseEntity<UserCircle> getCircleById(@PathVariable("circleName") String circleName) {
		//What if multiple users return by this method?
		//In the UserCircle table we may have multiple records for single circle
		UserCircle userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			//try to avoid creating object using 'new' operator.  Should use Autowire
			userCircle = new UserCircle();
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Circle does not exist");
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		}

		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}

	@PostMapping("/add-user-to-circle/{circleName}")
	public UserCircle addUserToCircle(@RequestBody String emailId, @PathVariable("circleName") String circleName) {
		//What if multiple users return by this method?
		//In the UserCircle table we may have multiple records for single circle
		UserCircle userCircle = userCircleDAO.getCircleByName(circleName);
		
		//Where are you adding emailId to userCircle? 
		//At the time of subscribing, a new record should be created in the UserCircle Table with emailId, circleName
		//and other default values like date of joining.  Where are you adding these?
		if (userCircleDAO.addCircle(userCircle) == true) {
			userCircle.setErrorCode("200");
			userCircle.setErrorMessage("User " + emailId + " is added Successfully to the Circle " + circleName);
		} else {
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("The Circle " + circleName + "does not Exists");
		}
		return userCircle;
	}

	//Use meaningful method name as you are not deleting by id  :  If it is related to unsubscribe, can keep unSubScribeFromCircle
	//url params says deactivate, but method name deleteCircleById.
	@PutMapping(value = "/deactivate-circle/{circleName}")
	public ResponseEntity<UserCircle> deleteCircleById(@PathVariable("circleName") String circleName) {
	//Note: It seems logic is not proper. 
		//How can we unsubcribe by sending only circle name?  Need to send email id also.
		
		UserCircle userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			userCircle = new UserCircle();
			userCircle.setErrorCode("404");
			userCircle.setErrorMessage("Circle does not exist");
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		}
		//use updateCircle as you are not deleting in the corresponding DAO.
		userCircleDAO.deleteCircle(userCircle);
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}
}
