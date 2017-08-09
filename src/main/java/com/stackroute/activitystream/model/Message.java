package com.stackroute.activitystream.model;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonFormat;


@Component
public class Message extends StatusCode{

	
	private int messageId;
	private String messageText;
	private String senderEmailId;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date sentDate;
	private long messageSize;
	private long maximumSize;
	private String messageType;
	private String receiverEmailId;
	private int circleId;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getSenderEmailId() {
		return senderEmailId;
	}

	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public long getMessageSize() {
		return messageSize;
	}

	public void setMessageSize(long messageSize) {
		this.messageSize = messageSize;
	}

	public long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getReceiverEmailId() {
		return receiverEmailId;
	}

	public void setReceiverEmailId(String receiverEmailId) {
		this.receiverEmailId = receiverEmailId;
	}

	public int getCircleId() {
		return circleId;
	}

	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	@Override
	public String toString() {
		return "UserMessage [messageId=" + messageId + ", messageText=" + messageText + ", senderEmailId="
				+ senderEmailId + ", sentDate=" + sentDate + ", messageSize=" + messageSize + ", maximumSize="
				+ maximumSize + ", messageType=" + messageType + ", receiverEmailId=" + receiverEmailId + ", circleId="
				+ circleId + "]";
	}
	
}
