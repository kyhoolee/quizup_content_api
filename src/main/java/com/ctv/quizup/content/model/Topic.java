package com.ctv.quizup.content.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class Topic {
	private String topicId;
	private String parentId;
	
	private String title;
	private String description;
	private String iconURL;
	
	private Date createdDate;
	private Date modifiedDate;
	
	private int numQuestion;
	
	
	
	public Topic() {
		
		this.topicId = "";
		this.parentId = "";
		this.title = "";
		this.description = "";
		
	}
	
	public Topic(Topic topic) {
		this.topicId = topic.getTopicId();
		this.parentId = topic.getParentId();
		this.title = topic.getTitle();
		this.description = topic.getDescription();
		this.iconURL = topic.getIconURL();
		this.createdDate = topic.getCreatedDate();
		this.modifiedDate = topic.getModifiedDate();
		this.numQuestion = topic.getNumQuestion();
	}
	
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getNumQuestion() {
		return numQuestion;
	}

	public void setNumQuestion(int numQuestion) {
		this.numQuestion = numQuestion;
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

	/**
	 * @return the iconURL
	 */
	public String getIconURL() {
		return iconURL;
	}

	/**
	 * @param iconURL the iconURL to set
	 */
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
}
