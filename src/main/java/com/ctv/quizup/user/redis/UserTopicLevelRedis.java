package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.util.LoggerUtil;

public class UserTopicLevelRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserTopicLevelRedis" + "_error"); 
	
	public static final String USER_TOPIC_LEVEL_HASHES_KEY = "quizup_user:user_topic_level_hashes:"; // + userId
	public static final String USER_TOPIC_LEVEL_SORTED_KEY = "quizup_user:user_topic_level_sorted:"; // + topicId
	
	
	public String getUserLevelKey(String userId) {
		return USER_TOPIC_LEVEL_HASHES_KEY + userId;
	}
	
	public String getLevelSortedKey(String topicId) {
		return USER_TOPIC_LEVEL_SORTED_KEY + topicId;
	}
	
	public void updateUserTopicLevel(UserTopicLevel topicLevel) {
		
		
		this.updateUserLevelInTopic(topicLevel.getTotalXP(), topicLevel.getTopicId(), topicLevel.getUserId());
		this.updateUserTopicLevel(topicLevel.toString(), topicLevel.getTopicId(), topicLevel.getUserId());
	}
	
	public void removeUserTopicLevel(String userId, String topicId) {
		this.removeUserLevel(userId, topicId);
		this.removeUserLevelTopic(topicId, userId);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-SORTED-LIST IN EACH TOPIC RANKING
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void updateUserLevelInTopic(int xpScore, String topicId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getLevelSortedKey(topicId), xpScore, userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void removeUserLevelTopic(String topicId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zrem(this.getLevelSortedKey(topicId), userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public long getUserLevelRank(String topicId, String userId) {
		long rank = -1;
		Jedis jedis = RedisPool.getJedis();
		try {
			rank = 1 + jedis.zrevrank(this.getLevelSortedKey(topicId), userId);
			return rank;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return rank;
	}
	
	
	public List<String> getUserIdListFromTopicRank(String topicId, long startIndex, long endIndex) {
		/*GET USER FROM REDIS*/
		List<String> userLevelList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			Set<String> userSet = new HashSet<String>();
			userSet = jedis.zrevrange(this.getLevelSortedKey(topicId), startIndex, endIndex);
			
			userLevelList.addAll(userSet);
			return userLevelList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userLevelList;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-LEVEL IN EACH USER PROFILE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void updateUserTopicLevel(String userTopicLevelJSON, String topicId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserLevelKey(userId), topicId, userTopicLevelJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserTopicLevel:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void removeUserLevel(String userId, String topicId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(this.getUserLevelKey(userId), topicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	

	public UserTopicLevel getUserTopicLevel(String userId, String topicId) {
		UserTopicLevel userTopicLevel = new UserTopicLevel(userId, topicId);
		
		/*GET USER FROM REDIS*/
		String uTopLev = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			uTopLev = jedis.hget(this.getUserLevelKey(userId), topicId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("getUserTopicLevel:jedis.hget:" + e);
			return new UserTopicLevel(userId, topicId);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		if(uTopLev == null) {
			return new UserTopicLevel(userId, topicId);
		}
		
		
		/*PARSE USER-LEVEL FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				userTopicLevel = mapper.readValue(uTopLev, UserTopicLevel.class);
				
			} catch (Exception e) {
				
				logger.error("getUserTopicLevel:ObjectMapper:" + e);
				return new UserTopicLevel(userId, topicId);
			}
		
		
		return userTopicLevel;
		
	}

	public List<UserTopicLevel> getUserLevelByUserId(String userId) {
		List<UserTopicLevel> userLevel = new ArrayList<UserTopicLevel>();
		
		/*GET USER FROM REDIS*/
		List<String> userLevelList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userLevelList = jedis.hvals(this.getUserLevelKey(userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER-LEVEL FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		for(String userAchieve : userLevelList) {
			try {
				UserTopicLevel uLevel = mapper.readValue(userAchieve, UserTopicLevel.class);
				
				userLevel.add(uLevel);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return userLevel;
		
	}
}
