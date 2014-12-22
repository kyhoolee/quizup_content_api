package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class UserPlayStats {
	private int totalMatch;
	private int winMatch;
	private int tieMatch;
	private int loseMatch;
	
	private long totalQues;
	
	private long totalScore;
	private long answerTime;
	
	
	public UserPlayStats() {
		super();
		this.totalMatch = 0;
		this.winMatch = 0;
		this.loseMatch = 0;
		this.totalQues = 0;
		this.totalScore = 0;
		this.answerTime = 0;
	}
	
	public UserPlayStats(int totalMatch, 
			int winMatch, 
			int tieMatch,
			int loseMatch,
			long totalQues, long totalScore, long answerTime) {
		super();
		this.totalMatch = totalMatch;
		this.winMatch = winMatch;
		this.loseMatch = loseMatch;
		this.totalQues = totalQues;
		this.totalScore = totalScore;
		this.answerTime = answerTime;
	}
	
	
	public int getTotalMatch() {
		return totalMatch;
	}
	public void setTotalMatch(int totalMatch) {
		this.totalMatch = totalMatch;
	}
	public int getWinMatch() {
		return winMatch;
	}
	public void setWinMatch(int winMatch) {
		this.winMatch = winMatch;
	}
	public int getLoseMatch() {
		return loseMatch;
	}
	public void setLoseMatch(int loseMatch) {
		this.loseMatch = loseMatch;
	}
	public long getTotalQues() {
		return totalQues;
	}
	public void setTotalQues(long totalQues) {
		this.totalQues = totalQues;
	}
	public long getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}
	public long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(long answerTime) {
		this.answerTime = answerTime;
	}
	
	/**
	 * @return the tieMatch
	 */
	public int getTieMatch() {
		return tieMatch;
	}

	/**
	 * @param tieMatch the tieMatch to set
	 */
	public void setTieMatch(int tieMatch) {
		this.tieMatch = tieMatch;
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
