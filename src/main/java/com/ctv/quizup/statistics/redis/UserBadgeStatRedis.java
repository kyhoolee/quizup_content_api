package com.ctv.quizup.statistics.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.redis.UserBadgeAchievRedis;
import com.ctv.quizup.util.LoggerUtil;

public class UserBadgeStatRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserBadgeRedis" + "_error");
	
	public static final String USER_ACHIEVEMENT_HASHES_KEY = "quizup_user:user_achievement_hashes:"; // + userId
	public static final String BADGE_HASHES = "quizup_badge:badge_hashes";
	
	private UserBadgeAchievRedis badgeAchiev;
	
	public UserBadgeStatRedis() {
		this.badgeAchiev = new UserBadgeAchievRedis();
	}

	public String getUserAchievementKey(String userId) {
		return USER_ACHIEVEMENT_HASHES_KEY + "userId";
	}
	
	public void updateUserBadge(UserBadgeAchiev achiev) {
		this.writeUserBadgeAchievementToRedis(achiev.toString(), achiev.getBadgeId() , achiev.getUserId());
	}
	

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BADGE-ACHIEVEMENT IN EACH USER PROFILE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public void writeUserBadgeAchievementToRedis(String userBadgeAchievementJSON, String badgeId, String userId) {
		this.badgeAchiev.writeUserBadgeAchievementToRedis(userBadgeAchievementJSON, badgeId, userId);
	}
	
	public UserBadgeAchiev getUserAchievement(String userId, String badgeId) {
		return this.badgeAchiev.getUserAchievement(userId, badgeId);
		
	}

	public List<UserBadgeAchiev> getUserAchievementByUserId(String userId) {
		return this.badgeAchiev.getUserAchievementByUserId(userId);
	}
}
