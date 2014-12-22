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

public class ChatRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("ChatRedis" + "_error");
	
	
	public static final String USER_CHAT_LIST_KEY = "quizup_user_comm:user_chat_sorted:"; //
	public static final String USER_TALK_SORTED_KEY = "quizup_user_comm:user_chat_talk_sorted:"; // + userId
	
	public String getUserMessageKey(String userId, String talkId) {
		return USER_CHAT_LIST_KEY + userId + ":" + talkId;
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
	
	public void removeUserTalk(String userId, String talkId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zrem(this.getTalkSortedKey(userId), talkId);
			jedis.zrem(this.getTalkSortedKey(talkId), userId);
			
			jedis.del(this.getUserMessageKey(userId, talkId));
			jedis.del(this.getUserMessageKey(talkId, userId));
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void writeMessageToSortedSet(Message message, String userId, String talkId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getUserMessageKey(userId, talkId), message.getCreatedDate().getTime(), message.toString());
			jedis.zadd(this.getUserMessageKey(talkId, userId), message.getCreatedDate().getTime(), message.toString());
			
//			jedis.lpush(this.getUserMessageKey(userId, talkId), message.toString());
//			jedis.lpush(this.getUserMessageKey(talkId, userId), message.toString());
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	
	public List<Message> getUserMessage(String userId, String talkId) {
		List<Message> messageList = this.getUserMessageByTalkId(userId, talkId, 0, -1);
		this.removeUserMessage(userId, talkId);
		return messageList;
	}
	
	private void removeUserMessage(String userId, String talkId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			// Remove from sorted-set
			// jedis.zremrangeByRank(this.getUserMessageKey(userId, talkId), 0, -1);
			jedis.del(this.getUserMessageKey(userId, talkId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void removeUserMessageByTalkId(String userId, String talkId, int start, int end) {
		Jedis jedis = RedisPool.getJedis();
		try {
			// Remove from sorted-set
			jedis.zremrangeByRank(this.getUserMessageKey(userId, talkId), -1-end, -1-start);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	private List<Message> getUserMessageByTalkId(String userId, String talkId, int startIndex, int endIndex) {
		List<Message> talkMessage = new ArrayList<Message>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> messList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			messList = jedis.zrevrange(this.getUserMessageKey(userId, talkId), startIndex, endIndex);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/		
		ObjectMapper mapper = new ObjectMapper(); 
		for(String messJSON : messList) {
			try {
				Message message = mapper.readValue(messJSON, Message.class);
				talkMessage.add(message);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return talkMessage;
		
	}
	
	public List<String> getTalkIdList(String userId) {
		List<String> userTalk = new ArrayList<String>();
		
		/*GET TalkId FROM REDIS*/
		Set<String> talkSet = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get talkId from new to old
			talkSet = jedis.zrevrange(this.getTalkSortedKey(userId), 0, -1);
			userTalk.addAll(talkSet);
			
			// // Remove talk-id from sorted-set
			//jedis.zremrangeByRank(this.getTalkSortedKey(userId), 0, -1);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		
		return userTalk;
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
