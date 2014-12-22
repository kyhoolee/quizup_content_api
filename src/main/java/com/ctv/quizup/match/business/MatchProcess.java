package com.ctv.quizup.match.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.redis.QuestionRedis;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchContent;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.match.redis.MatchBaseInfoRedis;
import com.ctv.quizup.match.redis.MatchLogRedis;
import com.ctv.quizup.match.redis.MatchResultRedis;

public class MatchProcess {
	MatchBaseInfoRedis matchBaseRedis;
	MatchLogRedis matchLogRedis;
	MatchResultRedis matchResultRedis;
	
	
	
	public MatchProcess() {
		this.matchBaseRedis = new MatchBaseInfoRedis();
		this.matchLogRedis = new MatchLogRedis();
		this.matchResultRedis = new MatchResultRedis();
	}
	
	
	public boolean updateMatchBase(MatchBaseInfo matchBase) {
		this.matchBaseRedis.updateMatchBaseInfo(matchBase.toString(), matchBase.getMatchId());
		return true;
	}
	public boolean updateFirstMatchLog(MatchLog matchLog) {
		this.matchLogRedis.updateFirstMatchLog(matchLog, matchLog.getMatchId());
		return true;
	}
	public boolean updateSecondMatchLog(MatchLog matchLog) {
		this.matchLogRedis.updateSecondMatchLog(matchLog, matchLog.getMatchId());
		return true;
	}
	public boolean updateMatchResult(MatchResult matchResult) {
		this.matchResultRedis.updateMatchResult(matchResult.toString(), matchResult.getMatchId());
		
		
		return true;
	}
	
	public static class Score {
		private int firstScore;
		private int secondScore;
		

		public Score(int first, int second) {
			this.firstScore = first;
			this.secondScore = second;
		}
		
		public Score() {
			this.firstScore = 0;
			this.secondScore = 0;
		}
		
		public int getFirstScore() {
			return firstScore;
		}

		public void setFirstScore(int firstScore) {
			this.firstScore = firstScore;
		}

		public int getSecondScore() {
			return secondScore;
		}

		public void setSecondScore(int secondScore) {
			this.secondScore = secondScore;
		}

		
		public void incFirstScore() {
			this.firstScore += 1;
		}
		public void incSecondScore() {
			this.secondScore += 1;
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
	
	public boolean updateUserScore(MatchResult result) {
		MatchBaseInfo matchBase = this.matchBaseRedis.getMatchBaseInfoById(result.getMatchId());
		
		
		//this.updateUserScore(matchBase.getFirstUserId(), matchBase.getSecondUserId(), result.getResult());
		this.updateUserScore(matchBase.getFirstUserId(), matchBase.getSecondUserId(), result.getResult(), matchBase.getTopicId());
		
		
		return true;
	}
	
	public boolean updateUserScore(String firstId, String secondId, int result, String topicId) {
		switch(result) {
		case 0 :
			updateTieUserScore(firstId, secondId, topicId);
			updateTieUserScore(secondId, firstId, topicId);
			break;
		case 1 :
			updateWinUserScore(firstId, secondId, topicId);
			updateLoseUserScore(secondId, firstId, topicId);
			break;
		case -1:
			updateWinUserScore(secondId, firstId, topicId);
			updateLoseUserScore(firstId, secondId, topicId);
			break;
		}
		
		return true;
	}
	
	public void updateWinUserScore(String firstId, String secondId, String topicId) {
		MatchProcess.Score score = this.matchResultRedis.getUserScore(firstId, secondId);
		score.incFirstScore();
		this.matchResultRedis.updateUserScore(score, firstId, secondId);
		
		score = this.matchResultRedis.getUserTopicScore(topicId, firstId, secondId);
		score.incFirstScore();
		this.matchResultRedis.updateUserTopicScore(score, topicId, firstId, secondId);
	}
	public void updateTieUserScore(String firstId, String secondId, String topicId) {
		MatchProcess.Score score = this.matchResultRedis.getUserScore(firstId, secondId);
		score.incFirstScore();
		score.incSecondScore();
		this.matchResultRedis.updateUserScore(score, firstId, secondId);
		
		score = this.matchResultRedis.getUserTopicScore(topicId, firstId, secondId);
		score.incSecondScore();
		score.incSecondScore();
		this.matchResultRedis.updateUserTopicScore(score, topicId, firstId, secondId);
	}
	public void updateLoseUserScore(String firstId, String secondId, String topicId) {
		MatchProcess.Score score = this.matchResultRedis.getUserScore(firstId, secondId);
		score.incSecondScore();
		this.matchResultRedis.updateUserScore(score, firstId, secondId);
		
		score = this.matchResultRedis.getUserTopicScore(topicId, firstId, secondId);
		score.incSecondScore();
		this.matchResultRedis.updateUserTopicScore(score, topicId, firstId, secondId);
	}
	
	
	public void updateUserScore(MatchProcess.Score score, String firstId, String secondId, String topicId) {
		this.matchResultRedis.updateUserScore(score, firstId, secondId);
		this.matchResultRedis.updateUserTopicScore(score, topicId, firstId, secondId);
	}
	
	
	public MatchProcess.Score getUserScore(String firstId, String secondId) {
		return this.matchResultRedis.getUserScore(firstId, secondId);
	}
	
	public MatchProcess.Score getUserTopicScore(String topicId, String firstId, String secondId) {
		return this.matchResultRedis.getUserTopicScore(topicId, firstId, secondId);
	}
	
	
	public MatchBaseInfo getMatchBaseInfo(String matchId) {
		return this.matchBaseRedis.getMatchBaseInfoById(matchId);
	}
	public MatchLog getFirstMatchLog(String matchId) {
		return this.matchLogRedis.getFirstLogByMatchId(matchId);
	}
	
	public List<Question> getFirstMatchContent(String matchId) {
		List<Question> quesList = new ArrayList<Question>();
		
		MatchLog log = this.matchLogRedis.getFirstLogByMatchId(matchId);
		if(log == null)
			return null;
		
		List<MatchQuestionLog> quesLog = log.getQuesLog();
		QuestionRedis quesRedis = new QuestionRedis();
		for(MatchQuestionLog ques : quesLog) {
			Question question = quesRedis.getQuestionById(ques.getQuestionId());
			if(ques!= null) {
				quesList.add(question);
			}
		}
		
		return quesList;
	}
	
	public MatchLog getSecondMatchLog(String matchId) {
		return this.matchLogRedis.getSecondLogByMatchId(matchId);
	}
	public MatchResult getMatchResult(String matchId) {
		return this.matchResultRedis.getMatchResultById(matchId);
	}
	
	
	public boolean removeMatchBase(String matchId) {
		this.matchBaseRedis.removeMatchBase(matchId);
		return true;
	}
	public boolean removeMatchLog(String matchId){
		this.matchLogRedis.removeMatchLog(matchId);
		return true;
	}
	public boolean removeMatchResult(String matchId){
		this.matchResultRedis.removeMatchResult(matchId);
		return true;
	}
	public boolean removeMatch(String matchId) {
		this.removeMatchBase(matchId);
		this.removeMatchLog(matchId);
		this.removeMatchResult(matchId);
		return true;
	}
	
	
	public static void main(String[] args) {
		String matchId = "1";
		MatchBaseInfo matchBase = MatchBaseInfo.createMatchBaseInfo(matchId);
		System.out.println(matchBase.toString());
		
		String playerId = "1";
		MatchLog matchLog = MatchLog.createMatchLog(matchId, playerId);
		System.out.println(matchLog.toString());
		
		playerId = "2";
		matchLog = MatchLog.createMatchLog(matchId, playerId);
		System.out.println(matchLog.toString());
		
		MatchResult matchResult = MatchResult.createMatchResult(matchId);
		System.out.println(matchResult.toString());
		
		
		
	}
}
