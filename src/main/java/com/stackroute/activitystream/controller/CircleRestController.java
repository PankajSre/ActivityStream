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
	public ResponseEntity<?> createCircle(@RequestBody Circle circles) {

		if (circleDAO.addCircle(circle) == true) {
			return new ResponseEntity<Circle>(circle, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/get-all-circles")
	public ResponseEntity<?> getAllCircles()
	{
		List<Circle> allCircles=circleDAO.getAllCircles();
		for(Circle circle : allCircles)
		{   Link link= linkTo(CircleRestController.class).slash(circle.getCircleName()).withSelfRel();
			circle.add(link);
		}
		return new ResponseEntity<List<Circle>>(allCircles, HttpStatus.OK);
	}
	
	@PostMapping("/delete-circle/{circleName}")
	public ResponseEntity<?> deleteCircle(@PathVariable("circleName") String circleName)
	{
		circle=circleDAO.getCircleByName(circleName);
		if(circle !=null)
		{
			circleDAO.deleteCircle(circle.getCircleName(),circle.getOwnerEmailId());
			return new ResponseEntity<Circle>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/update-circle/{circleName}")
	public ResponseEntity<?> updateCircle(@PathVariable("circleName") String circleName)
	{
		circle=circleDAO.getCircleByName(circleName);
		if(circle !=null)
		{
			circleDAO.updateCircle(circle);
			return new ResponseEntity<Circle>(circle,HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
}
