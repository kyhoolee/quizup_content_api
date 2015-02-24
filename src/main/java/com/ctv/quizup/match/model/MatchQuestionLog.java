package com.ctv.quizup.match.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class MatchQuestionLog {
	private String matchId;
	
	private String questionId;
	private int questionOrder;
	
	private String playerId;
	
	private int point = 0;
	private int time = 0;
	
	private String answerContent;
	
	private Date createdDate;
	
	
	public MatchQuestionLog(String matchId, String questionId, int questionOrder, String playerId, int point, int time) {
		this.matchId = matchId;
		this.questionId = questionId;
		this.questionOrder = questionOrder;
		this.playerId = playerId;
		this.point = point;
		
		this.createdDate = new Date();
	}
	
	public MatchQuestionLog() {
		this.createdDate = new Date();
	}
	
	public static MatchQuestionLog createQuestionLog(String matchId, String questionId, int questionOrder, String playerId, int point, int time) {
		MatchQuestionLog log = new MatchQuestionLog(matchId, questionId, questionOrder, playerId, point, time);
		
		
		return log;
	}
	
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public int getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
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

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	
}
