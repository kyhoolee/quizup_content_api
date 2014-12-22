package com.ctv.quizup.comm.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.comm.model.Message;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MessageRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("MessageRedis" + "_error");
	
	
	public static final String USER_MESSAGE_HASHES_KEY = "quizup_user_comm:user_message_hashes:"; //
	public static final String USER_MESSAGE_SORTED_KEY = "quizup_user_comm:user_message_sorted:"; // + userId:talkId
	public static final String USER_TALK_SORTED_KEY = "quizup_user_comm:user_talk_sorted:"; // + userId
	
	public String getUserMessageKey() {
		return USER_MESSAGE_HASHES_KEY;
	}
	
	public String getMessageSortedKey(String userId, String talkId) {
		return USER_MESSAGE_SORTED_KEY + userId + ":" + talkId;
	}
	
	public String getTalkSortedKey(String userId) {
		return USER_TALK_SORTED_KEY + userId;
	}
	
	public void writeTalkToSortedSet(long timeStamp, String userId, String talkId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getTalkSortedKey(userId), timeStamp, talkId);
			jedis.zadd(this.getTalkSortedKey(talkId), timeStamp, userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void writeMessageToSortedSet(long timeStamp, String messageId, String userId, String talkId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getMessageSortedKey(userId, talkId), timeStamp, messageId);
			jedis.zadd(this.getMessageSortedKey(talkId, userId), timeStamp, messageId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeMessageToRedis(String messageJSON, String messageId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserMessageKey(), messageId, messageJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	
	
	public Message getMessageById(String userId, String talkId, String messageId) {
		Message message = null;
		
		/*GET USER FROM REDIS*/
		String uMess = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			uMess = jedis.hget(this.getUserMessageKey(), messageId);
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
				message = mapper.readValue(uMess, Message.class);
				
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		
		
		return message;
		
	}

	public List<Message> getUserMessageByTalkId(String userId, String talkId, int startIndex, int endIndex) {
		List<Message> talkMessage = new ArrayList<Message>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> messList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			messList = jedis.zrevrange(this.getMessageSortedKey(userId, talkId), startIndex, endIndex);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/		
		for(String messId : messList) {
			try {
				Message uMess = this.getMessageById(userId, talkId, messId);
				talkMessage.add(uMess);
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		}
		
		return talkMessage;
		
	}
	
	public List<String> getTalkIdListByIndex(String userId, int startIndex, int endIndex) {
		List<String> userTalk = new ArrayList<String>();
		
		/*GET TalkId FROM REDIS*/
		Set<String> talkSet = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get talkId from new to old
			talkSet = jedis.zrevrange(this.getTalkSortedKey(userId), startIndex, endIndex);
			userTalk.addAll(talkSet);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userTalk;
	}
	
	public List<String> getTalkIdListByTime(String userId, long startTime, long endTime) {
		List<String> userTalk = new ArrayList<String>();
		
		/*GET TalkId FROM REDIS*/
		Set<String> talkSet = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get talkId from new to old
			talkSet = jedis.zrevrangeByScore(this.getTalkSortedKey(userId), endTime, startTime);
			userTalk.addAll(talkSet);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userTalk;
	}
}
