package com.ctv.quizup.user.business.impl;

import java.util.ArrayList;
import java.util.List;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.statistics.badge.BadgeComputeUtils;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.redis.BadgeRedis;
import com.ctv.quizup.user.redis.UserBadgeAchievRedis;

public class UserBadgeProcess {
	UserBadgeAchievRedis userBadgeRedis;
	BadgeRedis badgeRedis;
	//BadgeCompute badgeCompute;
	MatchProcess matchProcess;
	
	
	public UserBadgeProcess() {
		this.userBadgeRedis = new UserBadgeAchievRedis();
		this.badgeRedis = new BadgeRedis();
		this.matchProcess = new MatchProcess();
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public List<Badge> getBadgeList() {
		return badgeRedis.getBadgeList();
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBadgeAchiev> getUserNewBadgeList(String userId, String matchId, boolean isFirstUser) {
		List<UserBadgeAchiev> userBadgeList = new ArrayList<UserBadgeAchiev>();
		
		// CONTEXT-INFORMATION
		MatchBaseInfo matchBaseInfo = this.matchProcess.getMatchBaseInfo(matchId);
		
		
		MatchLog matchLog = null;
		if(isFirstUser)
			matchLog = this.matchProcess.getFirstMatchLog(matchId);
		else 
			matchLog = this.matchProcess.getSecondMatchLog(matchId);
		
		
		MatchResult matchResult = this.matchProcess.getMatchResult(matchId);
		if(!isFirstUser) {
			matchResult.setResult(-matchResult.getResult());
		}
		
			
		
		// CALCULATE USER-BADGE-PROGRESS BASED ON CONTEXT
		
		
		return userBadgeList;
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBadgeAchiev> getUserShortBadgeList(String userId) {
		List<UserBadgeAchiev> userBadgeList = new ArrayList<UserBadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		
		return userBadgeList;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBadgeAchiev> getUserFullBadgeList(String userId) {
		List<UserBadgeAchiev> userBadgeList = new ArrayList<UserBadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		
		for(int i = 0 ; i < userBadgeList.size() ; i ++) {
			String badgeId = userBadgeList.get(i).getBadgeId();
			userBadgeList.get(i).setBadge(this.badgeRedis.getBadgeById(badgeId));
		}
		
		return userBadgeList;
	}
	/**
	 * 
	 * @param userId
	 * @param badgeId
	 * @return
	 */
	public UserBadgeAchiev getUserBadge(String userId, String badgeId) {
		UserBadgeAchiev userBadge = null;//new UserBadgeAchievement();
		
		userBadge = this.userBadgeRedis.getUserAchievement(userId, badgeId);
		
		return userBadge;
	}
	

	
	/**
	 * 
	 * @param userId
	 * @param badge
	 * @return
	 */
	public boolean updateUserBadge(String userId, UserBadgeAchiev badge) {
		
		this.userBadgeRedis.writeUserBadgeAchievementToRedis(badge.toString(), badge.getBadgeId(), userId);
		
		return true;
	}
}
