package com.ctv.quizup.user.redis;



import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.util.LoggerUtil;

public class UserBaseInfoRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserBaseInfoRedis" + "_error");
	
	public static final String USER_BASE_INFO_HASHES = "quizup_user:user_base_info_hashes"; // + topicId
	public static final String USER_NOTIFICATION_HASHES = "quizup_user:user_notification_hashes";
	
	public void writeUserNotification(String userId, String regId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(USER_NOTIFICATION_HASHES, userId, regId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public String getUserRegId(String userId) {
		/*GET USER-REGISTER FROM REDIS*/
		String regId = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			regId = jedis.hget(USER_NOTIFICATION_HASHES, userId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return regId;
	}
	
	
	
	public void writeUserToRedis(String userJSON, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(USER_BASE_INFO_HASHES, userId, userJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public UserBaseInfo getUserById(String userId) {
		logger.info(userId);
		UserBaseInfo user = null;
		
		/*GET USER FROM REDIS*/
		String userJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			userJSON = jedis.hget(USER_BASE_INFO_HASHES, userId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		
			try {
				user = mapper.readValue(userJSON, UserBaseInfo.class);
			} catch (Exception e) {
				logger.error(e);
				System.out.println(e.toString());
			}
		
		
		return user;
		
	}
}
