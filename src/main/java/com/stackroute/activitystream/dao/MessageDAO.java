package com.stackroute.activitystream.dao;

import java.util.List;

import com.stackroute.activitystream.model.Message;

public interface MessageDAO {

	boolean sendMessage(Message message);
	List<Message> getAllMessages();
}
