package com.ctv.quizup.user.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class GameItemRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("GameItemRedis" + "_error");

	public static final String USER_ITEM_HASHES_KEY = "quizup_user:user_item_hashes:"; //727126420669934

	public String getUserItemKey(String userId) {
		return USER_ITEM_HASHES_KEY + userId;
	}




	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-ITEM IN EACH USER PROFILE
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeUserItem(String userId, String item, long value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserItemKey(userId), item, value + "");

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public long getUserItem(String userId, String item) {
		
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			String value = jedis.hget(this.getUserItemKey(userId),item);
			return Long.parseLong(value);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
			return 0;
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
		return 0;


	}

	
	public void resetUserItem(String userId, String item) {
		this.writeUserItem(userId, item, 0);
	}
	
	public void addUserItem(String userId, String item, long add) {
		long value = this.getUserItem(userId, item);
		this.writeUserItem(userId, item, value + add);
	}
	
	public boolean checkCostUserItem(String userId, String item, long cost) {
		long value = this.getUserItem(userId, item);
		if(value < cost) {
			return false;
		}
		
		this.writeUserItem(userId, item, value - cost);
		return true;
	}
}
