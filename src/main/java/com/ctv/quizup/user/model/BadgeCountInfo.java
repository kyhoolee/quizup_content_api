package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class BadgeCountInfo {
	
	
	public static enum CountType {
		Match,
		
		Match_Win,
		Match_Tie,
		Match_Lose,
		
		Match_Perfect,
		Match_Good,
		Match_Counter,
		Match_1_Answer,
		Match_0_Answer,
		
		Topic,
		
		Topic_Music,
		Topic_Sport,
		Topic_Education,
		Topic_Entertainment,
		Topic_Nature,
		Topic_LifeStyle,
		Topic_Science,
		
		Topic_Poem,
		Topic_Map,
		Topic_Singer,
		Topic_Painter,
		
		Frequent_Win,
		Frequent_Answer,
		
		Compete_Location
	}
	
	private String badgeId;
	private CountType countType;
	
	private int countScore;
	private int condition;// For Topic-Level-Condition
	
	public BadgeCountInfo() {
		this.condition = -1; // no condition
	}

	public BadgeCountInfo(String badgeId, CountType countType, int countScore, int condition) {
		this.badgeId = badgeId;
		this.countType = countType;
		this.countScore = countScore;
		this.condition = condition;
	}
	
	
	
	public String getBadgeId() {
		return badgeId;
	}


	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}


	public CountType getCountType() {
		return countType;
	}


	public void setCountType(CountType countType) {
		this.countType = countType;
	}


	public int getCountScore() {
		return countScore;
	}


	public void setCountScore(int countScore) {
		this.countScore = countScore;
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
	 * @return the condition
	 */
	public int getCondition() {
		return condition;
	}



	/**
	 * @param condition the condition to set
	 */
	public void setCondition(int condition) {
		this.condition = condition;
	}


}
