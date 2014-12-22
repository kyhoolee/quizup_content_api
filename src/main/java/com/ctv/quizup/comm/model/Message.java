package com.ctv.quizup.comm.model;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

public class Message {
	private String messageId;
	
	private String senderId;
	private String receiverId;
	
	private String content;
	
	private Date createdDate;
	
	public static void main(String[] args) {
		long date = 1414057542470l;
		
		Message mess = new Message("senderId", "receiverId", "content", date);
		System.out.println(mess.toString());
	}
	
	public Message() {
		this.messageId = UUID.randomUUID().toString();
		this.senderId = "";
		this.receiverId = "";
		this.content = "";
		this.createdDate = new Date();
	}
	
	public Message(String senderId, String receiverId, String content) {
		this.messageId = UUID.randomUUID().toString();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
		this.createdDate = new Date();
		
	}
	
	public Message(String senderId, String receiverId, String content, long timeStamp) {
		this.messageId = UUID.randomUUID().toString();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
		this.createdDate = new Date(timeStamp);
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@Override
	public String toString(){
		String result = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(this);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
