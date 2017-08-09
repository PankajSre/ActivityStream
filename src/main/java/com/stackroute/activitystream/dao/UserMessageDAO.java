package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.UserMessage;


public interface UserMessageDAO {

	boolean sendMessage(UserMessage message);
	boolean deleteMessage(int messageId, String emailId);
	List<UserMessage> getMyMessages(String emailId);
	UserMessage getMessageByMessageId(int messageId);
	List<UserMessage> getAllMessageByCircleName(String circleName);
}
