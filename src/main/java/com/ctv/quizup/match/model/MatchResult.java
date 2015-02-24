package com.ctv.quizup.match.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class MatchResult {
	private String matchId;
	
	private boolean isFinished;
	
	private int result; // -1, 0, 1 --> lose tie win
	
	private Date createdDate;
	
	public MatchResult() {
		this.matchId = "";
		this.isFinished = true;
		this.result = 0;
		this.createdDate = new Date();
	}
	
	public MatchResult(String matchId, int result, Date createdDate) {
		this.matchId = matchId;
		this.isFinished = true;
		this.result = result;
		this.createdDate = createdDate;
	}
	
	
	public MatchResult(String matchId) {
		this.matchId = matchId;
		this.createdDate = new Date();
		this.result = 0;
		this.isFinished = true;
		
	}
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
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
	public static MatchResult createMatchResult(String matchId) {
		MatchResult result = new MatchResult(matchId);
		
		return result;
	}
}
