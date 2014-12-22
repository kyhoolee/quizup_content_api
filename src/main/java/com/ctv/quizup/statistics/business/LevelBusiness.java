package com.ctv.quizup.statistics.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.TopicLevelCompute;
import com.ctv.quizup.statistics.redis.UserLevelStatRedis;
import com.ctv.quizup.user.business.impl.UserTopicProcess;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.util.LoggerUtil;

public class LevelBusiness extends MatchBaseBusiness {
	public static Logger logger = LoggerUtil.getDailyLogger("LevelBusiness" + "_error");
	
	private UserTopicProcess userTopic;
	private UserLevelStatRedis levelStatRedis;
	
	public LevelBusiness() {
		super();
		this.userTopic = new UserTopicProcess();
		this.levelStatRedis = new UserLevelStatRedis();
	}
	
	public void process(String matchId) {
		this.initProcess(matchId);
		this.updateCount();
	}
	
	public Map<String, UserTopicLevel> processLevel(String matchId) {
		
		this.initProcess(matchId);
		return this.countLevel();
	}
	
	public List<UserTopicLevel> processLevel(String matchId, String userId) {
		this.initProcess(matchId);
		return this.updateCountList(userId);
	}
	
	public UserTopicLevel processUserLevel(String matchId, String userId) {
		logger.info("matchId: " + matchId);
		logger.info("userId: " + userId);
		this.initProcess(matchId);
		return this.updateCount(userId);
		
	}
	
	public void initProcess(String matchId) {
		super.initProcess(matchId);
		
		
		
	}
	
	public void updateCount() {
		String firstId = super.getMatchBase().getFirstUserId();
		String secondId = super.getMatchBase().getSecondUserId();

		String topicId = super.getMatchBase().getTopicId();
		
		List<MatchQuestionLog> firstLog = super.getFirstLog().getQuesLog();
		List<MatchQuestionLog> secondLog = super.getSecondLog().getQuesLog();
		
		UserTopicLevel firstLevel = this.userTopic.getUserTopic(firstId, topicId);
		UserTopicLevel secondLevel = this.userTopic.getUserTopic(secondId, topicId);
		
		
		int result = super.getMatchResult().getResult();
		
		
		
		this.updateUserLevel(firstLevel, result, firstLog);
		this.updateUserLevel(secondLevel, -result, secondLog);
	}
	
	public UserTopicLevel updateCount(String userId) {
		String firstId = super.getMatchBase().getFirstUserId();
		String secondId = super.getMatchBase().getSecondUserId();
		int result = super.getMatchResult().getResult();
		String topicId = super.getMatchBase().getTopicId();
		List<MatchQuestionLog> log = null;
		
		UserTopicLevel userLevel = this.userTopic.getUserTopic(userId, topicId);
		
		if(this.levelStatRedis.checkUserMatch(super.getMatchBase().getMatchId(), userId)) {
			return userLevel;
		} else {
			this.levelStatRedis.writeUserMatch(super.getMatchBase().getMatchId(), userId);
		}
		
		if(userId.equalsIgnoreCase(firstId)) {
			log = super.getFirstLog().getQuesLog();
		} else if(userId.equalsIgnoreCase(secondId)) {
			log = super.getSecondLog().getQuesLog();
			result = -result;
		} else {
			return userLevel;
		}

		return this.updateUserLevel(userLevel, result, log);
	}
	
	public List<UserTopicLevel> updateCountList(String userId) {
		List<UserTopicLevel> levelList = new ArrayList<UserTopicLevel>();
		
		String firstId = super.getMatchBase().getFirstUserId();
		String secondId = super.getMatchBase().getSecondUserId();
		int result = super.getMatchResult().getResult();
		String topicId = super.getMatchBase().getTopicId();
		List<MatchQuestionLog> log = null;
		
		UserTopicLevel userLevel = this.userTopic.getUserTopic(userId, topicId);
		levelList.add(userLevel);
		
		if(this.levelStatRedis.checkUserMatch(super.getMatchBase().getMatchId(), userId)) {
			levelList.add(userLevel);
			return levelList;
		} else {
			this.levelStatRedis.writeUserMatch(super.getMatchBase().getMatchId(), userId);
		}
		
		if(userId.equalsIgnoreCase(firstId)) {
			log = super.getFirstLog().getQuesLog();
		} else if(userId.equalsIgnoreCase(secondId)) {
			log = super.getSecondLog().getQuesLog();
			result = -result;
		} else {
			levelList.add(userLevel);
			return levelList;
		}
		levelList.add(userLevel);
		levelList.add(this.updateUserLevel(userLevel, result, log));
		return levelList;
	}
	
	public boolean isComputed(String matchId) {
		
		return false;
	}
	
	public Map<String, UserTopicLevel> countLevel() {
		this.updateCount();
		
		Map<String, UserTopicLevel> result = new HashMap<String, UserTopicLevel>();
		String topicId = super.getMatchBase().getTopicId();
		String firstId = super.getMatchBase().getFirstUserId();
		String secondId = super.getMatchBase().getSecondUserId();
		
		UserTopicLevel first = this.userTopic.getUserTopic(firstId, topicId);
		UserTopicLevel second = this.userTopic.getUserTopic(secondId, topicId);
		
		result.put(firstId, first);
		result.put(secondId, second);
		
		return result;
	}
	
	public UserTopicLevel updateUserLevel(UserTopicLevel userLevel, int result, List<MatchQuestionLog> log) {
		logger.info("userTopicLevel: " + userLevel.toString());
		logger.info("result: " + result);
		logger.info("log: " + log.toString());
		UserTopicLevel topicLevel = TopicLevelCompute.updateXPFromMatchResult(userLevel, result, log);
		logger.info("ComputedLevel: " + userLevel.toString());
		this.userTopic.updateUserTopicLevel(topicLevel);
		return topicLevel;
	}
	
	public UserTopicLevel computeUserLevel(UserTopicLevel userLevel, int result, List<MatchQuestionLog> log) {
		
		UserTopicLevel topicLevel = TopicLevelCompute.updateXPFromMatchResult(userLevel, result, log);
		
		return topicLevel;
	}

	
	public List<UserTopicLevel> getLevelByMatch(String userId, String matchId) {
		List<UserTopicLevel> levelList = new ArrayList<UserTopicLevel>();
		
		this.initProcess(matchId);
		
		String firstId = super.getMatchBase().getFirstUserId();
		String secondId = super.getMatchBase().getSecondUserId();
		int result = super.getMatchResult().getResult();
		String topicId = super.getMatchBase().getTopicId();
		List<MatchQuestionLog> log = null;
		
		UserTopicLevel userLevel = this.userTopic.getUserTopic(userId, topicId);
		levelList.add(userLevel);
		
		if(this.levelStatRedis.checkUserMatch(super.getMatchBase().getMatchId(), userId)) {
			levelList.add(userLevel);
			return levelList;
		} else {
			this.levelStatRedis.writeUserMatch(super.getMatchBase().getMatchId(), userId);
		}
		
		if(userId.equalsIgnoreCase(firstId)) {
			log = super.getFirstLog().getQuesLog();
		} else if(userId.equalsIgnoreCase(secondId)) {
			log = super.getSecondLog().getQuesLog();
			result = -result;
		} else {
			levelList.add(userLevel);
			return levelList;
		}
		
		levelList.add(userLevel);
		levelList.add(this.updateUserLevel(userLevel, result, log));
		return levelList;
	}


}