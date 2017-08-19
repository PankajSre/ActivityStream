package com.stackroute.activitystream.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.UNAUTHORIZED);
		}
		
	}

	@GetMapping(value = "/circle-by-name/{circleName}")
	public ResponseEntity<UserCircle> getCircleById(@PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			userCircle = new UserCircle();
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}

	@PostMapping("/add-user-to-circle/{circleName}")
	public ResponseEntity<?> addUserToCircle(@RequestBody String emailId, @PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircleDAO.addCircle(userCircle) == true) {
			return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		
	}

	@PutMapping(value = "/deactivate-circle/{circleName}")
	public ResponseEntity<?> deleteCircleById(@PathVariable("circleName") String circleName) {
	    userCircle = userCircleDAO.getCircleByName(circleName);
		if (userCircle == null) {
			userCircle = new UserCircle();
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		userCircleDAO.deleteCircle(userCircle);
		return new ResponseEntity<UserCircle>(userCircle, HttpStatus.OK);
	}
	@GetMapping("/getAllCircles")
	public ResponseEntity<?> getAllCircles()
	{
		List<UserCircle> allCircles=userCircleDAO.getAllCircles();
		for(UserCircle circle : allCircles)
		{   Link link= linkTo(UserCircleRestController.class).slash(circle.getCircleName()).withSelfRel();
			circle.add(link);
		}
		return new ResponseEntity<List<UserCircle>>(allCircles, HttpStatus.OK);
		
	}
}
