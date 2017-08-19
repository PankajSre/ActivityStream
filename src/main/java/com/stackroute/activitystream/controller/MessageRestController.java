package com.stackroute.activitystream.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.MessageDAO;
import com.stackroute.activitystream.model.Message;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {

	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private Message message;
	
	@RequestMapping(value = "/sendMessages", method = RequestMethod.POST)
	public ResponseEntity<?> sendMessage(@RequestBody Message message) {
		if (messageDAO.sendMessage(message) == true) {
			return new ResponseEntity<Message>(message, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping("/updateMessage")
	public ResponseEntity<Message> updateMessage(@RequestBody Message msg)
	{
		if(msg==null)
		{
			return new ResponseEntity<Message>(msg,HttpStatus.NOT_FOUND);
		}
		messageDAO.updateMessage(msg);
		return new ResponseEntity<Message>(msg,HttpStatus.OK);
		
	}
	@GetMapping(value="/getAllMessages/{receiverEmailId}")
	public List<Message> getAllmessages(@PathVariable("receiverEmailId") String receiverEmailId)
	{
		List<Message> messages=messageDAO.getAllMessages(receiverEmailId);
		for(Message message : messages)
		{   Link link1= linkTo(UserMessageRestController.class).slash(message.getMessageId()).withSelfRel();
			Link link=linkTo(UserMessageRestController.class).slash(message.getCircleName()).withSelfRel();
			message.add(link);
			message.add(link1);
		}
		return messages;
	}
}
