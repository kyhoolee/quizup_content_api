package com.ctv.quizup.statistics.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class TriggerStatsRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserLevelStatRedis" + "_error");
	
	public static final String USER_LEVEL_STAT_SET = "quizup_user:user_level_stat_hashes:"; // + userId
	public static final String USER_BADGE_STAT_SET = "quizup_user:user_badge_stat_hashes:"; // + userId
	
	
	
	public String getUserLevelMatchKey(String userId) {
		return USER_LEVEL_STAT_SET + userId;
	}
	
	public String getUserBadgeMatchKey(String userId) {
		return USER_BADGE_STAT_SET + userId;
	}
	
	public boolean checkUserBadgeMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			return jedis.sismember(this.getUserLevelMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		return false;
	}
	
	
	public void writeUserBadgeMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(this.getUserLevelMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public boolean checkUserLevelMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			return jedis.sismember(this.getUserLevelMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		return false;
	}
	
	
	public void writeUserLevelMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(this.getUserLevelMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}


}
