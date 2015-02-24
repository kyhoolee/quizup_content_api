package com.ctv.quizup.statistics;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.util.LoggerUtil;

public class TopicLevelCompute {
	public static Logger logger = LoggerUtil.getDailyLogger("TopicLevelCompute" + "_error");
	
	public static final int MATCH_LEN = 7;
	
	public static final int WIN_XP = 100;
	public static final int TIE_XP = 50;
	public static final int LOSE_XP = 0;
	
	public static final int FINISH_XP = 40;


	
	public static int getAddXPFromMatchResult(String matchId, int result, List<MatchQuestionLog> log) {
		int addXP = getAddXPFromMatch(result);
		logger.info("-- +resultXP: " + addXP);
		addXP += getAddXPFromLog(matchId, log);
		logger.info("-- +logXP: " + addXP);
		if(log.size() == MATCH_LEN) {
			addXP += FINISH_XP;
		}
		logger.info("-- +finishXP: " + addXP);
		
		return addXP;
		
	}
	
	public static UserTopicLevel updateXPFromMatchResult(String matchId, UserTopicLevel level, int result, List<MatchQuestionLog> log) {
		int addXP = getAddXPFromMatch(result);
		logger.info("-- +resultXP: " + addXP);
		addXP += getAddXPFromLog(matchId, log);
		logger.info("-- +logXP: " + addXP);
		if(log.size() == MATCH_LEN) {
			addXP += FINISH_XP;
		}
		logger.info("-- +finishXP: " + addXP);
		return updateXP(level, addXP);
		
	}

	public static UserTopicLevel updateXP(UserTopicLevel topicLevel, int addXP) {
		UserTopicLevel newLevel = new UserTopicLevel();

		newLevel.setUserId(topicLevel.getUserId());
		newLevel.setTopicId(topicLevel.getTopicId());
		newLevel.setCreatedDate(topicLevel.getCreatedDate());
		newLevel.setModifiedDate(new Date());
		newLevel.setPlayTimes(topicLevel.getPlayTimes() + 1);

		int totalXP = topicLevel.getTotalXP() + addXP;
		newLevel.setTotalXP(totalXP);

		
		int currentLevelBaseXP = topicLevel.getLevelBaseXP();
		if(totalXP >= currentLevelBaseXP) {
			newLevel.setLevel(topicLevel.getLevel() + 1);
			newLevel.setLevelXP(totalXP - currentLevelBaseXP);
			newLevel.setLevelBaseXP(currentLevelBaseXP + (newLevel.getLevel() + 1) * 60 + 80);
		} else {
			newLevel.setLevel(topicLevel.getLevel());
			newLevel.setLevelBaseXP(topicLevel.getLevelBaseXP());
			newLevel.setLevelXP(topicLevel.getLevelXP() + addXP);
		}
		//System.out.println(newLevel.toString());
		logger.info("-- oldLevel: " + topicLevel.toString());
		logger.info("-- newLevel: " + newLevel.toString());

		return newLevel;
	}

	public static int getAddXPFromMatch(int result) {
		int addXP = 0;

		switch (result) {
		case -1: // lose
			return LOSE_XP;
			
		case 0: // tie
			return TIE_XP; 
			
		case 1: // win
			return WIN_XP;
			
		}
		
		return addXP;
	}

	public static int getAddXPFromLog(String matchId, List<MatchQuestionLog> matchLog) {
		int addXP = 0;

		for (MatchQuestionLog log : matchLog) {
			if(log.getMatchId().equalsIgnoreCase(matchId)) {
				addXP += log.getPoint();
			}
		}

		return addXP;
	}

	public static void main(String[] args) {
		int totalXP = 100;
		UserTopicLevel topicLevel = new UserTopicLevel();
		topicLevel.setTotalXP(totalXP);
		int addXP = 100;
		topicLevel = TopicLevelCompute.updateXP(topicLevel, addXP);

		System.out.println(topicLevel);

	}

}
