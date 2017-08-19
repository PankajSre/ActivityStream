package com.stackroute.activitystream.controller;

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
import com.stackroute.activitystream.dao.UserMessageDAO;
import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.UserMessage;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@RestController
@RequestMapping("/api/userMessage")
public class UserMessageRestController {


	@Autowired
	UserMessageDAO userMessageDAO;
	
	@RequestMapping(value = "/sendUserMessage", method = RequestMethod.POST)
	public ResponseEntity<?> sendMessage(@RequestBody UserMessage userMessage) {
		if (userMessageDAO.sendMessage(userMessage) == true) {
			return new ResponseEntity<UserMessage>(userMessage, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@RequestMapping(value = "/userMessageById/{messageId}", method = RequestMethod.GET)
	public ResponseEntity<?> getMessageById(@PathVariable("messageId") int messageId) {
		UserMessage userMessage=userMessageDAO.getMessageByMessageId(messageId);
		if (userMessage == null) {
			userMessage = new UserMessage();
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userMessage.add(linkTo(UserMessageRestController.class).slash(userMessage.getMessageId()).withSelfRel());
		return new ResponseEntity<UserMessage>(userMessage, HttpStatus.OK);
	}
	@RequestMapping(value = "/deleteUserMessage", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMessage(@RequestBody UserMessage userMessage) {
		userMessageDAO.deleteMessage(userMessage.getMessageId(), userMessage.getReceiverEmailId());
		userMessage.add(linkTo(methodOn(UserMessageRestController.class).deleteMessage(userMessage)).withSelfRel());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/getUserMessages")
	public List<UserMessage> getAllMessagesByUser(@RequestBody UserMessage userMessage) {
		
		List<UserMessage> userMessages= userMessageDAO.getMyMessages(userMessage.getReceiverEmailId());	
		for(UserMessage message : userMessages)
		{   Link link1= linkTo(UserMessageRestController.class).slash(message.getMessageId()).withSelfRel();
			Link link=linkTo(UserMessageRestController.class).slash(message.getCircleName()).withSelfRel();
			message.add(link);
			message.add(link1);
		}
		return userMessages;
	}
	@GetMapping("/getAllMessagesByCircleName/{circleName}")
	public List<UserMessage> getAllMessagesByCircleName(@PathVariable("circleName") String circleName)
	{
		List<UserMessage> messageList=userMessageDAO.getAllMessageByCircleName(circleName);
		for(UserMessage message : messageList)
		{   Link link1= linkTo(UserMessageRestController.class).slash(message.getMessageId()).withSelfRel();
			Link link=linkTo(UserMessageRestController.class).slash(message.getCircleName()).withSelfRel();
			message.add(link);
			message.add(link1);
		}
		return messageList;
	}
	
	
}
