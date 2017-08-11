package com.stackroute.activitystream.controller;

import java.util.List;

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
import com.stackroute.activitystream.model.Circle;

@RestController
@RequestMapping("/api/circles")
public class CircleRestController {

	@Autowired
	private CircleDAO circleDAO;
	@Autowired
	private Circle circle;
	
	@PostMapping(value = "/create-circle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Circle> createCircle(@RequestBody Circle circles) {

		if (circleDAO.addCircle(circle) == true) {
			circle.setStatusCode("200");
			circle.setStatusMessage("you have Successfully created the Circle ");
		} else {
			circle.setStatusCode("404");
			circle.setStatusMessage("Your Circle has not been created");
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}
	
	@GetMapping("/get-all-circles")
	public List<?> getAllCircles()
	{
		List<Circle> allCircles=circleDAO.getAllCircles();
		if(allCircles.size()>0)
		{
		   circle.setStatusCode("200");
		   circle.setStatusMessage("Circles are Retrieved Successfully");
		   return allCircles;
		}
		else
		{
		circle.setStatusCode("404");
		circle.setStatusMessage("There are no Circles Available");
		return null;
		}
		
		
	}
	
	@PostMapping("/delete-circle/{circleName}")
	public ResponseEntity<?> deleteCircle(@PathVariable("circleName") String circleName)
	{
		circle=circleDAO.getCircleByName(circleName);
		if(circle ==null)
		{
			circle.setStatusCode("404");
			circle.setStatusMessage("The Circle with name "+circleName+" does not Exists");
		}
		circleDAO.deleteCircle(circle);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/update-circle/{circleName}")
	public ResponseEntity<?> updateCircle(@PathVariable("circleName") String circleName)
	{
		circle=circleDAO.getCircleByName(circleName);
		if(circle ==null)
		{
			circle.setStatusCode("404");
			circle.setStatusMessage("The Circle with name "+circleName+" does not Exists");
		}
		circleDAO.deleteCircle(circle);
		return new ResponseEntity<Circle>(circle,HttpStatus.OK);
	}
}
