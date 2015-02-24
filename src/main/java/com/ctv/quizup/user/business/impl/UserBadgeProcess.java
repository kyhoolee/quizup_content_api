package com.ctv.quizup.user.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.statistics.badge.BadgeComputeUtils;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
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
	
	
	public static class FullBadge extends Badge {
		private CountType countType;
		private int countScore;
		private int condition;// For Topic-Level-Condition
		
		public FullBadge() {
			super();
			this.condition = -1; // no condition
		}
		
		public FullBadge(Badge badge) {
			super(badge);
			this.condition = -1; // no condition
		}

		public FullBadge(String badgeId, CountType countType, int countScore, int condition) {
			super();
			this.countType = countType;
			this.countScore = countScore;
			this.condition = condition;
		}
		
		public FullBadge(Badge badge, CountType countType, int countScore, int condition) {
			super(badge);
			this.countType = countType;
			this.countScore = countScore;
			this.condition = condition;
		}
		
		public FullBadge(Badge badge, BadgeCountInfo count) {
			super(badge);
			this.condition = count.getCondition();
			this.countScore = count.getCountScore();
			this.countType = count.getCountType();
		}

		public CountType getCountType() {
			return countType;
		}
		public void setCountType(CountType countType) {
			this.countType = countType;
		}
		public int getCountScore() {
			return countScore;
		}
		public void setCountScore(int countScore) {
			this.countScore = countScore;
		}
		/**
		 * @return the condition
		 */
		public int getCondition() {
			return condition;
		}
		/**
		 * @param condition the condition to set
		 */
		public void setCondition(int condition) {
			this.condition = condition;
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
	
	/**
	 * 
	 * @return
	 */
	public List<Badge> getBadgeList() {
		return badgeRedis.getBadgeList();
	}
	
	public List<FullBadge> getFullBadgeList() {
		List<Badge> badges = this.badgeRedis.getBadgeList();
		List<FullBadge> fullBadges = new ArrayList<UserBadgeProcess.FullBadge>();
		for(Badge badge : badges) {
			BadgeCountInfo count = this.badgeRedis.getBadgeCountById(badge.getBadgeId());
			FullBadge full = new FullBadge(badge, count);
			fullBadges.add(full);
		}
		
		return fullBadges;
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserNewBadgeList(String userId, String matchId, boolean isFirstUser) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		
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
	public List<BadgeAchiev> getUserShortBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		
		return userBadgeList;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserFreshBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		userBadgeList = this.userBadgeRedis.getFreshAchiev(userId);
		return userBadgeList;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		return userBadgeList;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserFinishedBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		
		List<BadgeAchiev> result = new ArrayList<BadgeAchiev>();
		
		for(BadgeAchiev achiev : userBadgeList) {
			if(achiev.isFinished()) {
				//userBadgeList.remove(achiev);
				result.add(achiev);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserFreshFullBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getFreshAchiev(userId);
		
		for(int i = 0 ; i < userBadgeList.size() ; i ++) {
			String badgeId = userBadgeList.get(i).getBadgeId();
			userBadgeList.get(i).setBadge(this.badgeRedis.getBadgeById(badgeId));
		}
		
		return userBadgeList;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<BadgeAchiev> getUserFullBadgeList(String userId) {
		List<BadgeAchiev> userBadgeList = new ArrayList<BadgeAchiev>();
		
		userBadgeList = this.userBadgeRedis.getUserAchievementByUserId(userId);
		
		for(int i = 0 ; i < userBadgeList.size() ; i ++) {
			String badgeId = userBadgeList.get(i).getBadgeId();
			userBadgeList.get(i).setBadge(this.badgeRedis.getBadgeById(badgeId));
		}
		
		return userBadgeList;
	}
	
	public void removeUserAchiev(String userId) {
		this.userBadgeRedis.removeUserAchievs(userId);
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param badgeId
	 * @return
	 */
	public BadgeAchiev getUserBadge(String userId, String badgeId) {
		BadgeAchiev userBadge = null;//new UserBadgeAchievement();
		
		userBadge = this.userBadgeRedis.getUserAchievement(userId, badgeId);
		
		return userBadge;
	}
	

	
	/**
	 * 
	 * @param userId
	 * @param badge
	 * @return
	 */
	public boolean updateUserBadge(String userId, BadgeAchiev badge) {
		
		this.userBadgeRedis.writeUserBadgeAchievementToRedis(badge.toString(), badge.getBadgeId(), userId);
		
		return true;
	}


}
