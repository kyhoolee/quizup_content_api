package com.ctv.quizup.user.redis;



import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.User;


public class UserRedis {
	public static final String USER_HASHES = "quizup_user:user_hashes"; // + topicId
	
	public void writeUserToRedis(String userJSON, String userId) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hset(USER_HASHES, userId, userJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public User getUserById(String userId) {
		User user = null;
		
		/*GET USER FROM REDIS*/
		String userJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userJSON = jedis.hget(USER_HASHES, userId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		
			try {
				user = mapper.readValue(userJSON, User.class);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return user;
		
	}
	
}
