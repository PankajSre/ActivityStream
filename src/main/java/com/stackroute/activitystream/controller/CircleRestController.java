package com.stackroute.activitystream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;
@RestController
@RequestMapping("/api/circles")
public class CircleRestController {

	@Autowired
	private CircleDAO circleDAO;
	@Autowired
	private Circle circle;
	
	
	@GetMapping("/get-all-circles")
	public List<Circle> getAllCircles()
	{
		List<Circle> allCircles=circleDAO.getAllCircles();
		if(allCircles.size()>0)
		{
		   circle.setErrorCode("200");
		   circle.setErrorMessage("Circles are Retrieved Successfully");
		}
		circle.setErrorCode("404");
		circle.setErrorMessage("There are no Circles Available");
		
		return allCircles;
	}
	
}
