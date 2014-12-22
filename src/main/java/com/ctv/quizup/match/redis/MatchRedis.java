package com.ctv.quizup.match.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.model.Match;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MatchRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("ChatRedis" + "_error");
	public static final String TOPIC_MATCH_HASHES = "quizup_match:match_hashes:"; // + topicId 
	
	public static final String USER_MATCH_SORTED_SET = "quizup_match:user_match_sorted:"; // + userId;
	public static final String USER_MATCH_TOPIC_HASHES = "quizup_match:user_match_topic:"; // + userId;
	
	public String getTopicMatchKey(String topicId) {
		return TOPIC_MATCH_HASHES + topicId;
	}
	public String getUserMatchSortedKey(String userId) {
		return USER_MATCH_SORTED_SET + userId;
	}
	public String getUserTopicMatchKey(String userId) {
		return USER_MATCH_TOPIC_HASHES + userId;
	}

	
	public void writeUserTopicMatch(String userTopicMatch, String topicId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserTopicMatchKey(userId), topicId, userTopicMatch);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public List<String> getUserTopicMatch(String userId) {
		List<String> userTopicMatch = new ArrayList<String>();
		
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> topicSet = jedis.hkeys(this.getUserTopicMatchKey(userId));
			userTopicMatch.addAll(topicSet);
			
			return userTopicMatch;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userTopicMatch;
	}
	
	public void writeUserMatchSorted(String matchId, String userId, long timeStamp) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getUserMatchSortedKey(userId), timeStamp, matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	

	public void writeTopicMatchToRedis(String matchJSON, String matchId, String topicId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getTopicMatchKey(topicId), matchId, matchJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	/**
	 * 
	 * @param topicId
	 * @param matchId
	 * @return
	 */
	public Match getTopicMatchById(String topicId, String matchId) {
		Match match = null;
		
		/*GET USER FROM REDIS*/
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(this.getTopicMatchKey(topicId), matchId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		
			try {
				match = mapper.readValue(matchJSON, Match.class);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return match;
		
	}
	

	
	
	public List<Match> getUserMatchListByIndex(String topicId, String userId, int startIndex, int endIndex) {
		List<Match> userMatch = new ArrayList<Match>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> userMatchList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userMatchList = jedis.zrevrange(this.getUserMatchSortedKey(userId), startIndex, endIndex);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/	
		for(String matchId : userMatchList) {
			try {
				Match uMatch = this.getTopicMatchById(topicId, matchId);
				userMatch.add(uMatch);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return userMatch;
	}
	
	public List<Match> getUserMatchListByTime(String topicId, String userId, long startTime, long endTime) {
		List<Match> userMatch = new ArrayList<Match>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> userMatchList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userMatchList = jedis.zrevrangeByScore(this.getUserMatchSortedKey(userId), endTime, startTime);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/	
		for(String matchId : userMatchList) {
			try {
				Match uAct = this.getTopicMatchById(topicId, matchId);
				userMatch.add(uAct);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return userMatch;
	}
}
