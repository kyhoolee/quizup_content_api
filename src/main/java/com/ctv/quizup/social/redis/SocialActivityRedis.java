package com.ctv.quizup.social.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class SocialActivityRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("ActivityRedis" + "_error");
	
	public static final String USER_ACTIVITY_HASHES = "quizup_activity:user_activity_hashes:"; 
	public static final String USER_ACTIVITY_SORTED = "quizup_activity:user_activity_sorted:"; // + userId

	private String getActivityHashesKey() {
		return USER_ACTIVITY_HASHES;
	}
	
	private String getActivitySortedKey(String userId) {
		return USER_ACTIVITY_SORTED + userId;
	}
	
	public void writeActivityToHashes(String activityJSON, String activityId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getActivityHashesKey(), activityId, activityJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void writeActivityToSorted(String userId, String activityId, long timeStamp) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getActivitySortedKey(userId), timeStamp, activityId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}


}
