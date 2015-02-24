package com.ctv.quizup.match.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.business.TopicProcess;
import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.QuestionRedis;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.match.model.Match;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchContent;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.match.redis.MatchBaseInfoRedis;
import com.ctv.quizup.match.redis.MatchLogRedis;
import com.ctv.quizup.match.redis.MatchResultRedis;
import com.ctv.quizup.match.sql.MatchSQL;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.model.UserBaseInfo;

public class MatchProcess {
	MatchBaseInfoRedis matchBaseRedis;
	MatchLogRedis matchLogRedis;
	MatchResultRedis matchResultRedis;
	
	public MatchBaseInfoRedis getMatchBaseRedis() {
		return matchBaseRedis;
	}


	public void setMatchBaseRedis(MatchBaseInfoRedis matchBaseRedis) {
		this.matchBaseRedis = matchBaseRedis;
	}


	public MatchLogRedis getMatchLogRedis() {
		return matchLogRedis;
	}


	public void setMatchLogRedis(MatchLogRedis matchLogRedis) {
		this.matchLogRedis = matchLogRedis;
	}


	public MatchResultRedis getMatchResultRedis() {
		return matchResultRedis;
	}


	public void setMatchResultRedis(MatchResultRedis matchResultRedis) {
		this.matchResultRedis = matchResultRedis;
	}

	MatchSQL matchSQL;
	
	
	
	public MatchProcess() {
		this.matchBaseRedis = new MatchBaseInfoRedis();
		this.matchLogRedis = new MatchLogRedis();
		this.matchResultRedis = new MatchResultRedis();
		
		this.matchSQL = new MatchSQL();
	}
	
	
	public boolean updateMatchBase(MatchBaseInfo matchBase) {
		this.matchBaseRedis.updateMatchBaseInfo(matchBase.toString(), matchBase.getMatchId());
		this.matchBaseRedis.updateMatchSorted(matchBase.getMatchId(), matchBase.getCreatedDate().getTime());
		
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
		score.incFirstScore();
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
		if(log == null) {
			log = this.getFirstMatchLogSQL(matchId);
			if(log == null) {
				return null;
			}
		}
		
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
	
	public static class FullMatch extends Match {
		private UserBaseInfo firstUser;
		private UserBaseInfo secondUser;
		private Topic topic;
		
		
		public FullMatch(Match match) {
			super(match);
			this.init(match);
		}

		public void init(Match match) {
			UserServiceProcess userService = new UserServiceProcess();
			this.firstUser = userService.getUser(match.getBaseInfo().getFirstUserId());
			this.secondUser = userService.getUser(match.getBaseInfo().getSecondUserId());
			
			TopicRedis topicProcess = new TopicRedis();
			this.topic = topicProcess.getTopicById(match.getBaseInfo().getTopicId());
		}

		public UserBaseInfo getFirstUser() {
			return firstUser;
		}


		public void setFirstUser(UserBaseInfo firstUser) {
			this.firstUser = firstUser;
		}


		public UserBaseInfo getSecondUser() {
			return secondUser;
		}


		public void setSecondUser(UserBaseInfo secondUser) {
			this.secondUser = secondUser;
		}


		public Topic getTopic() {
			return topic;
		}


		public void setTopic(Topic topic) {
			this.topic = topic;
		}
		
		
	}
	

	
	public Match getMatch(String matchId) {
		if(this.getMatchBaseInfo(matchId) == null) {
			return null;
		}
		
		Match match = new Match();
		match.setBaseInfo(this.getMatchBaseInfo(matchId));
		match.setFirstLog(this.getFirstMatchLog(matchId));
		match.setSecondLog(this.getSecondMatchLog(matchId));
		match.setResult(this.getMatchResult(matchId));
		match.setContent(new MatchContent(this.getFirstMatchContent(matchId)));
		
		
		
		return match;
	}
	
	public FullMatch getFullMatch(String matchId) {
		Match match = this.getMatch(matchId);
		if(match == null) 
			return null;
		FullMatch full = new FullMatch(match);
		
		return full;
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

	}
	
	
	public MatchLog getFirstMatchLogSQL( String matchId) {
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getFirstLog();
	}

	public MatchLog getSecondMatchLogSQL(String matchId) {
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getSecondLog();
	}

	public MatchBaseInfo getMatchBaseSQL(String matchId) {
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getBaseInfo();
	}

	public MatchResult getMatchResultSQL(String matchId) {
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getResult();
	}
	
	public Match getMatchSQL(String matchId) {
		List<Match> matchs = this.matchSQL.getMatchById(matchId);
		if(matchs.size() > 0)
			return matchs.get(0);
		else return null;
	}
	
	public FullMatch getFullMatchSQL(String matchId) {
		Match match = this.getMatchSQL(matchId);
		if(match != null) {
			FullMatch full = new FullMatch(match);
			return full;
		} else
			return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// REDIS TO SQL
	
	public MatchLog getFirstLog( String matchId) {
		MatchLog log = this.getFirstMatchLog(matchId);
		if(log != null) {
			return log;
		}
		
		
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getFirstLog();
	}

	public MatchLog getSecondLog(String matchId) {
		MatchLog log = this.getSecondMatchLog(matchId);
		if(log != null) {
			return log;
		}
		
		
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getSecondLog();
	}

	public MatchBaseInfo getBase(String matchId) {
		MatchBaseInfo base = this.getMatchBaseInfo(matchId);
		if(base != null) {
			return base;
		}
		
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getBaseInfo();
	}

	public MatchResult getResult(String matchId) {
		MatchResult result = this.getMatchResult(matchId);
		if(result != null) {
			return result;
		}
		
		Match match = this.getMatchSQL(matchId);
		if(match == null)
			return null;
		
		return match.getResult();
	}
	
	public Match getMatchRS(String matchId) {
		Match match = this.getMatch(matchId);
		if(match != null) {
			return match;
		}
		
		List<Match> matchs = this.matchSQL.getMatchById(matchId);
		if(matchs.size() > 0)
			return matchs.get(0);
		else return null;
	}
	
	public FullMatch getFullMatchRS(String matchId) {
		FullMatch full = this.getFullMatch(matchId);
		if(full != null) {
			return full;
		}
		
		Match match = this.getMatchSQL(matchId);
		if(match != null) {
			full = new FullMatch(match);
			return full;
		} else
			return null;
	}
	
	
}
