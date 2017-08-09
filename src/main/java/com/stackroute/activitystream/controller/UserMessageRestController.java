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
@RequestMapping("/api/message")
public class UserMessageRestController {


	@Autowired
	UserMessageDAO userMessageDAO;
	
	@Autowired
	MessageDAO messageDAO;
	
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public ResponseEntity<UserMessage> sendMessage(@RequestBody UserMessage userMessage) {

		Message message=new Message();
		message.setMessageId(userMessage.getMessageId());
		message.setMessageText(userMessage.getMessageText());
		message.setCircleName(userMessage.getCircleName());
		message.setMaximumSize(userMessage.getMaximumSize());
		message.setMessageSize(userMessage.getMessageSize());
		message.setMessageType(userMessage.getMessageType());
		message.setReceiverEmailId(userMessage.getReceiverEmailId());
		message.setSenderEmailId(userMessage.getSenderEmailId());
		message.setSentDate(userMessage.getSentDate());
		if (userMessageDAO.sendMessage(userMessage) == true && messageDAO.sendMessage(message)==true) {
			userMessage.setErrorCode("200");
			userMessage.setErrorMessage("Your Message have been sent Successfully");
		} else {
			userMessage.setErrorCode("404");
			userMessage.setErrorMessage("Your Circle has  been created");
		}
		return new ResponseEntity<UserMessage>(userMessage, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/messageById/{messageId}", method = RequestMethod.GET)
	public ResponseEntity<UserMessage> getMessageById(@PathVariable("messageId") int messageId) {
		UserMessage userMessage=userMessageDAO.getMessageByMessageId(messageId);
		if (userMessage == null) {
			userMessage = new UserMessage();
			userMessage.setErrorCode("404");
			userMessage.setErrorMessage("Circle does not exist");
			return new ResponseEntity<UserMessage>(userMessage, HttpStatus.OK);
		}
		System.out.println("MessaageId :"+messageId);
		userMessage.add(linkTo(UserMessageRestController.class).slash(userMessage.getMessageId()).withSelfRel());
		System.out.println("MessaageId :"+messageId);
		return new ResponseEntity<UserMessage>(userMessage, HttpStatus.OK);
	}
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMessage(@RequestBody UserMessage userMessage) {
		userMessageDAO.deleteMessage(userMessage.getMessageId(), userMessage.getReceiverEmailId());
		userMessage.add(linkTo(methodOn(UserMessageRestController.class).deleteMessage(userMessage)).withSelfRel());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/getMyMessages")
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
	
	@GetMapping(value="/getAllMessages")
	public List<Message> getAllmessages()
	{
		List<Message> messages=messageDAO.getAllMessages();
		for(Message message : messages)
		{   Link link1= linkTo(UserMessageRestController.class).slash(message.getMessageId()).withSelfRel();
			Link link=linkTo(UserMessageRestController.class).slash(message.getCircleName()).withSelfRel();
			message.add(link);
			message.add(link1);
		}
		return messages;
	}
}
