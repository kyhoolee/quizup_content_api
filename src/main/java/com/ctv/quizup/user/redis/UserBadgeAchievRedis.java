package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.util.LoggerUtil;

public class UserBadgeAchievRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserBadgeRedis" + "_error");
	
	public static final String USER_ACHIEVEMENT_HASHES_KEY = "quizup_user:user_achievement_hashes:"; // + userId
	public static final String BADGE_HASHES = "quizup_badge:badge_hashes";

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
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserAchievementKey(userId), badgeId, userBadgeAchievementJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public UserBadgeAchiev getUserAchievement(String userId, String badgeId) {
		UserBadgeAchiev userAchievement = null;
		
		/*GET USER FROM REDIS*/
		String userAchieve = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userAchieve = jedis.hget(this.getUserAchievementKey(userId), badgeId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				userAchievement = mapper.readValue(userAchieve, UserBadgeAchiev.class);
				
			} catch (Exception e) {
				logger.error(e);
				return null;
			}
		
		
		return userAchievement;
		
	}

	public List<UserBadgeAchiev> getUserAchievementByUserId(String userId) {
		List<UserBadgeAchiev> userAchievement = new ArrayList<UserBadgeAchiev>();
		
		/*GET USER FROM REDIS*/
		List<String> userAchieveList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userAchieveList = jedis.hvals(this.getUserAchievementKey(userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		for(String userAchieve : userAchieveList) {
			try {
				UserBadgeAchiev uAchiev = mapper.readValue(userAchieve, UserBadgeAchiev.class);
				userAchievement.add(uAchiev);
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		}
		
		return userAchievement;
		
	}
}
