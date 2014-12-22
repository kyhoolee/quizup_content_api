package com.ctv.quizup.match.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class Match {
	private MatchBaseInfo baseInfo;
	private MatchContent content;
	
	private MatchLog firstLog;
	private MatchLog secondLog;
	
	private MatchResult result;
	
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
	public MatchLog getLog() {
		return firstLog;
	}
	public void setLog(MatchLog log) {
		this.firstLog = log;
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
	public MatchLog getSecondLog() {
		return secondLog;
	}
	public void setSecondLog(MatchLog secondLog) {
		this.secondLog = secondLog;
	}

}
