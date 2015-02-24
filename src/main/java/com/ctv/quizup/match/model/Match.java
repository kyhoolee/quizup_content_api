package com.ctv.quizup.match.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class Match {
	private MatchBaseInfo baseInfo;
	private MatchContent content;
	
	private MatchLog firstLog;
	private MatchLog secondLog;
	
	private MatchResult result;
	
	public Match() {
		
	}
	
	public Match(Match match) {
		this.baseInfo = match.getBaseInfo();
		this.firstLog = match.getFirstLog();
		this.secondLog = match.getSecondLog();
		this.content = match.getContent();
		this.result = match.getResult();
		
	}
	public Match(String matchId, String firstId, String secondId,
			String topicId, int matchResult, MatchLog first, MatchLog second,
			Date createdDate) {
		
		this.baseInfo = new MatchBaseInfo(matchId, createdDate, firstId, secondId, topicId);
		this.firstLog = first;
		this.secondLog = second;
		this.result = new MatchResult(matchId, matchResult, createdDate);
	}

	public MatchBaseInfo getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(MatchBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	public MatchContent getContent() {
		return content;
	}
	public void setContent(MatchContent content) {
		this.content = content;
	}
	public MatchLog getFirstLog() {
		return firstLog;
	}
	public void setFirstLog(MatchLog log) {
		this.firstLog = log;
	}
	public MatchLog getSecondLog() {
		return secondLog;
	}
	public void setSecondLog(MatchLog secondLog) {
		this.secondLog = secondLog;
	}
	public MatchResult getResult() {
		return result;
	}
	public void setResult(MatchResult result) {
		this.result = result;
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
