package com.ctv.quizup.statistics.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class UserLevelStatRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserLevelStatRedis" + "_error");
	
	public static final String USER_LEVEL_STAT_SET = "quizup_user:user_level_stat_hashes:"; // + userId
	
	
	public String getUserMatchKey(String userId) {
		return USER_LEVEL_STAT_SET + userId;
	}
	
	public boolean checkUserMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			return jedis.sismember(this.getUserMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		return false;
	}
	
	
	public void writeUserMatch(String matchId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(this.getUserMatchKey(userId), matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}


}
