package com.ctv.quizup.comm.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class FeedBack {
	
	private String feedBackId;
	
	private String userId;
	
	private String targetType;
	private String targetId;
	
	private String content;
	
	private Date createdDate;
	
	

	public FeedBack() {
		super();
		this.feedBackId = "1";
		this.userId = "2";
		this.targetType = "3";
		this.targetId = "4";
		this.content = "5";
		this.createdDate = new Date();
	}



	public FeedBack(String feedBackId, String userId, String targetType,
			String targetId, String content, Date createdDate) {
		super();
		this.feedBackId = feedBackId;
		this.userId = userId;
		this.targetType = targetType;
		this.targetId = targetId;
		this.content = content;
		this.createdDate = createdDate;
	}



	public String getFeedBackId() {
		return feedBackId;
	}



	public void setFeedBackId(String feedBackId) {
		this.feedBackId = feedBackId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getTargetType() {
		return targetType;
	}



	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}



	public String getTargetId() {
		return targetId;
	}



	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}



	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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

	public static void main(String[] args) {
		FeedBack feed = new FeedBack();
		System.out.println(feed);
	}

}
