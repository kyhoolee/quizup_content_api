package com.ctv.quizup.user.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.statistics.TopicLevelCompute;


public class UserTopicLevel {
	
	private String userId;
	private String topicId;

	private int totalXP = 0;
	private int level = 1;
	private int levelXP = 0;
	private int levelBaseXP = 200;
	
	private int playTimes;
	
	private Date createdDate;
	private Date modifiedDate;
	
	public UserTopicLevel(UserTopicLevel topicLevel) {
		this.userId = topicLevel.getUserId();
		this.topicId = topicLevel.getTopicId();
		
		this.totalXP = topicLevel.getTotalXP();
		this.level = topicLevel.getLevel();
		this.levelXP = topicLevel.getLevelXP();
		this.levelBaseXP = topicLevel.getLevelBaseXP();
		
		this.playTimes = topicLevel.getPlayTimes();
		
		this.createdDate = topicLevel.getCreatedDate();
		this.modifiedDate = topicLevel.getModifiedDate();
		
		
	}
	
	public UserTopicLevel(String userId, String topicId) {
		this.userId = userId;
		this.topicId = topicId;
		this.modifiedDate = new Date();
		this.createdDate = new Date();
	}
	
	public UserTopicLevel() {
		this.totalXP = 0;
		this.level = 1;
		this.levelXP = 0;
		this.levelBaseXP = 200;//TopicLevelCompute.levelBaseXP(level);
		this.modifiedDate = new Date();
		this.createdDate = new Date();
	}
	
	public UserTopicLevel(String userId, String topicId, int totalXP) {
		this.userId = userId;
		this.topicId = topicId;
		
		this.totalXP = totalXP;
		
		this.modifiedDate = new Date();
		this.createdDate = new Date();
	}
	
	public UserTopicLevel(String userId, String topicId, int totalXP, Date createdDate) {
		this.userId = userId;
		this.topicId = topicId;
		
		this.totalXP = totalXP;
		
		this.modifiedDate = new Date();
		this.createdDate = createdDate;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTotalXP() {
		return totalXP;
	}
	public void setTotalXP(int totalXP) {
		this.totalXP = totalXP;
	}
	public int getLevelXP() {
		return levelXP;
	}
	public void setLevelXP(int levelXP) {
		this.levelXP = levelXP;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getPlayTimes() {
		return playTimes;
	}
	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
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
	 * @return the levelBaseXP
	 */
	public int getLevelBaseXP() {
		return levelBaseXP;
	}

	/**
	 * @param levelBaseXP the levelBaseXP to set
	 */
	public void setLevelBaseXP(int levelBaseXP) {
		this.levelBaseXP = levelBaseXP;
	}
}
