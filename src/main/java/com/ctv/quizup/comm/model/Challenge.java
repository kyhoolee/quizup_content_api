package com.ctv.quizup.comm.model;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;

public class Challenge {
	public static class ChallengeStatus {
		public static final String UNSEEN = "unseen";
		public static final String SEEN = "seen";
		public static final String DONE = "done";
	}
	
	
	private String challengeId;
	private String topicId;
	private String matchId;
	
	private String userId;
	private String rivalId;
	
	private String content;
	private String status; // seen, unseen
	private Date createdDate;
	
	public Challenge() {
		this.createdDate = new Date();
	}
	
	public Challenge(Challenge challenge) {
		this.challengeId = challenge.getChallengeId();
		
		this.topicId = challenge.getTopicId();
		this.matchId = challenge.getMatchId();
		
		this.userId = challenge.getUserId();
		this.rivalId = challenge.getRivalId();
		
		this.content = challenge.getContent();
		this.status = challenge.getStatus();
		this.createdDate = challenge.getCreatedDate();
		
	}
	
	public Challenge(String topicId, String matchId, String userId, String rivalId) {
		this.createdDate = new Date();
		
		this.matchId = matchId;
		
		this.challengeId = UUID.randomUUID().toString();
		this.topicId = topicId;
		this.userId = userId;
		this.rivalId = rivalId;
		
		this.content = "";
		this.status = ChallengeStatus.UNSEEN;
	}
	
	public String getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getRivalId() {
		return rivalId;
	}
	public void setRivalId(String rivalId) {
		this.rivalId = rivalId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
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
		Challenge chall = new Challenge("topic-1", "match-1", "user-1", "rival-1");
		
		System.out.println(chall.toString());
	}
	
	
}
