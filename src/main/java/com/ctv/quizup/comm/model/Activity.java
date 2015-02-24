package com.ctv.quizup.comm.model;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.comm.business.impl.ActivityProcess;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.model.UserBaseInfo;

public class Activity {
	public static class Content {
		private Object subject;
		private Object action;
		private Object object;
		private Object context;
		
		
		
		
		
		public Content() {
			super();
		}
		public Content(Object subject, Object action, Object object, Object context) {
			super();
			this.subject = subject;
			this.action = action;
			this.object = object;
			this.context = context;
		}
		
		public Object getSubject() {
			return subject;
		}
		public void setSubject(Object subject) {
			this.subject = subject;
		}
		public Object getAction() {
			return action;
		}
		public void setAction(Object action) {
			this.action = action;
		}
		/**
		 * @return the object
		 */
		public Object getObject() {
			return object;
		}
		/**
		 * @param object the object to set
		 */
		public void setObject(Object object) {
			this.object = object;
		}
		

		/**
		 * @return the context
		 */
		public Object getContext() {
			return context;
		}
		/**
		 * @param context the context to set
		 */
		public void setContext(Object context) {
			this.context = context;
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
	
	
	
	private String activityId;
	private String authorId;
	
	private ActivityProcess.ActivityType type;
	
	private String authorName;
	private Content content;
	private String matchId;
	//private String description;
	
	
	
	private Date createdDate;
	private Date modifiedDate;
	
	public Activity() {
		this.createdDate = new Date();
		this.modifiedDate = new Date();
		
		this.activityId = UUID.randomUUID().toString();
		
		this.content = new Content();
	}
	
	public Activity(String authorId) {
		this.createdDate= new Date();
		this.modifiedDate = new Date();
		
		this.activityId = UUID.randomUUID().toString();
		
		this.authorId = authorId;

		this.content = new Content();
	}
	
	public Activity(String authorId, Date createdDate) {
		this.createdDate= createdDate;
		this.modifiedDate = createdDate;
		
		this.activityId = UUID.randomUUID().toString();
		
		this.authorId = authorId;

		this.content = new Content();
	}
	
	public Activity(String authorId, Date createdDate, ActivityProcess.ActivityType type) {
		this.createdDate= createdDate;
		this.modifiedDate = createdDate;
		
		this.activityId = UUID.randomUUID().toString();
		
		this.authorId = authorId;
		this.type = type;

		this.content = new Content();
	}

	
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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


	/**
	 * @return the type
	 */
	public ActivityProcess.ActivityType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ActivityProcess.ActivityType type) {
		this.type = type;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
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
	



	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

}
