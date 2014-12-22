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

//	public static int updateLevel(int totalXP) {
//		// x(1) = 0 
//		// x(n+1) = x(n) + (n+1) * 60 + 80;
//
//		int level = 0;
//		
//		if(totalXP < 200)
//			return 1;
//		
//		level = 1 + ((totalXP - 190) / 60);
//
//		return level;
//	}

//	public static int updateLevelXP(int totalXP) {
//		int levelXP = 0;
//
//		levelXP = totalXP - ((totalXP - 70) / 60) * 60;
//
//		return levelXP;
//	}
//	
//	public static int levelBaseXP(int level) {
//		int xp = level * 60 + 70;
//		return xp;
//	}
	
	public static UserTopicLevel updateXPFromMatchResult(UserTopicLevel level, int result, List<MatchQuestionLog> log) {
		int addXP = getAddXPFromMatch(result);
		logger.info("-- +resultXP: " + addXP);
		addXP += getAddXPFromLog(log);
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

	public static int getAddXPFromLog(List<MatchQuestionLog> matchLog) {
		int addXP = 0;

		for (MatchQuestionLog log : matchLog) {
			//System.out.println(log.toString());
			addXP += log.getPoint();
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
