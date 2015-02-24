package com.ctv.quizup.match.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class MatchLog {
	private String matchId;
	
	private List<MatchQuestionLog> quesLog = new ArrayList<MatchQuestionLog>();
	
	public MatchLog() {
		this.quesLog = new ArrayList<MatchQuestionLog>();
	}
	
	public void cleanLog() {
		if(matchId == null) {
			return;
		}
		List<MatchQuestionLog> logs = new ArrayList<MatchQuestionLog>();
		
		for(int i = 0 ; i < quesLog.size() ; i ++) {
			if(matchId.equalsIgnoreCase(quesLog.get(i).getMatchId())) {
				logs.add(quesLog.get(i));
			}
		}
		
		this.quesLog = logs;
	}
	
//	public static MatchLog createMatchLog(String matchId, String playerId) {
//		MatchLog log = new MatchLog();
//		log.setMatchId(matchId);
//		
//		for(int i = 0 ; i < 7 ; i ++) {
//			MatchQuestionLog ques = new MatchQuestionLog(matchId, "" + i, i, playerId, 20); 
//			log.getQuesLog().add(ques);
//		}
//		
//		return log;
//	}
	
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public List<MatchQuestionLog> getQuesLog() {
		return quesLog;
	}

	public void setQuesLog(List<MatchQuestionLog> quesLogList) {
		this.quesLog = quesLogList;
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
