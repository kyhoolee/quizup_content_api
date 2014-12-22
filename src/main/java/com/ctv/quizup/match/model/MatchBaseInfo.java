package com.ctv.quizup.match.model;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;


public class MatchBaseInfo {
	private String matchId;
	
	private String firstUserId;
	private String secondUserId;
	
	private String topicId;
	
	
	private Date createdDate;
	private Date modifiedDate;
	
	public MatchBaseInfo() {
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}
	
	public MatchBaseInfo(String firstId, String secondId, String topicId) {
		this.firstUserId = firstId;
		this.secondUserId = secondId;
		this.topicId = topicId;
		
		this.matchId = UUID.randomUUID().toString();
		
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}
	
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getFirstUserId() {
		return firstUserId;
	}
	public void setFirstUserId(String firstUserId) {
		this.firstUserId = firstUserId;
	}
	public String getSecondUserId() {
		return secondUserId;
	}
	public void setSecondUserId(String secondUserId) {
		this.secondUserId = secondUserId;
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
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
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

	public static MatchBaseInfo createMatchBaseInfo(String matchId) {
		MatchBaseInfo matchBase = new MatchBaseInfo();
		matchBase.setMatchId(matchId);
		matchBase.setFirstUserId("1");
		matchBase.setSecondUserId("2");
		matchBase.setTopicId("1");
		
		
		return matchBase;
	}
}
