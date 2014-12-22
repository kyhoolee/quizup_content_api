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
import com.ctv.quizup.user.model.UserRelation;
import com.ctv.quizup.util.LoggerUtil;

public class UserRelationRedis {
	public static class RelationType {
		public static final String FRIEND_RELATION = "friend_relation";
	}
	
	public static Logger logger = LoggerUtil.getDailyLogger("UserRelationRedis" + "_error");
	public static final String USER_RELATION_HASHES = "quizup_user:user_relation_hashes:"; // + userId + ":" + type

//	public static final String USER_RELATION_SET = "quizup_user:user_relation_set:"; // + userId
//	
//	public String getUserRelationSetKey(String userId) {
//		return USER_RELATION_SET + userId;
//	}
//	
//
//	public void writeUserRelationTypeToRedis(String type, String userId) {
//		Jedis jedis = RedisPool.getJedis();
//		try {
//			jedis.sadd(this.getUserRelationSetKey(userId), type);
//			
//		} catch (JedisConnectionException ex) {
//			RedisPool.getIntance().returnBrokenResource(jedis);
//			
//		} catch (Exception e) {
//		} finally {
//			RedisPool.getIntance().returnResource(jedis);
//			
//		}
//	}
//
//	public Set<String> getUserRelationTypeList(String userId) {
//		
//		/*GET USER FROM REDIS*/
//		Set<String> typeSet = new HashSet<String>();
//		Jedis jedis = RedisPool.getJedis();
//		try {
//			// Add to sorted-set
//			typeSet = jedis.smembers(this.getUserRelationSetKey(userId));
//		} catch (JedisConnectionException ex) {
//			RedisPool.getIntance().returnBrokenResource(jedis);
//			
//		} catch (Exception e) {
//		} finally {
//			RedisPool.getIntance().returnResource(jedis);
//			
//		}
//		
//		
//		return typeSet;
//		
//	}
	
	public String getUserRelationHashesKey(String userId, String type) {
		return USER_RELATION_HASHES + userId + ":" + type;
	}
	
	public void updateUserRelation(String relationJSON, String userId, String targetId, String type){
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserRelationHashesKey(userId, type), targetId, relationJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void removeUserRelation(String userId, String targetId, String type) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(this.getUserRelationHashesKey(userId, type), targetId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
	}
	
	public Set<String> getRelationTarget(String userId, String type) {
		
		/*GET USER FROM REDIS*/
		Set<String> userRelationList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userRelationList = jedis.hkeys(this.getUserRelationHashesKey(userId, type));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userRelationList;
	}
	
	public List<UserRelation> getUserRelation(String userId, String type) {
		List<UserRelation> userRelation = new ArrayList<UserRelation>();
		
		/*GET USER FROM REDIS*/
		List<String> userRelationList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userRelationList = jedis.hvals(this.getUserRelationHashesKey(userId, type));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		for(String userRel : userRelationList) {
			try {
				UserRelation uRelation = mapper.readValue(userRel, UserRelation.class);
				userRelation.add(uRelation);
			} catch (Exception e) {
				logger.error(e);
				System.out.println(e.toString());
			}
		}
		
		return userRelation;
	}
}
