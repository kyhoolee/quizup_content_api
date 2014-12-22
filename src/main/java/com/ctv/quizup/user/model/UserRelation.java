package com.ctv.quizup.user.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.user.redis.UserRelationRedis;

public class UserRelation {
	private String userId;
	private String targetId;
	
	private String type;
	private String content;
	
	private Date createdDate;
	
	public UserRelation() {
		this.createdDate = new Date();
	}
	public UserRelation(String userId, String targetId, String type) {
		this.userId = userId;
		this.targetId = targetId;
		this.type = type;
		this.createdDate = new Date();
		this.content = "";
	}
	
	public static UserRelation createFriend(String userId, String friendId) {
		UserRelation relation = new UserRelation(userId, friendId, 
				UserRelationRedis.RelationType.FRIEND_RELATION);
		
		return relation;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
