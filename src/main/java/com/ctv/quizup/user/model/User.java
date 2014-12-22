package com.ctv.quizup.user.model;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class User {
	private UserBaseInfo baseInfo;
	private List<UserBadgeAchiev> achievement; 
	private List<UserTopicLevel> level;
	private List<UserRelation> relation;
	
	public UserBaseInfo getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(UserBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	public List<UserBadgeAchiev> getAchievement() {
		return achievement;
	}
	public void setAchievement(List<UserBadgeAchiev> achievement) {
		this.achievement = achievement;
	}
	public List<UserTopicLevel> getLevel() {
		return level;
	}
	public void setLevel(List<UserTopicLevel> level) {
		this.level = level;
	}
	public List<UserRelation> getRelation() {
		return relation;
	}
	public void setRelation(List<UserRelation> relation) {
		this.relation = relation;
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
