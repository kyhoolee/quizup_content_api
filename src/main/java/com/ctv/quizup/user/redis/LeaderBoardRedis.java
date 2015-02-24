package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.util.ComUtils;
import com.ctv.quizup.util.LoggerUtil;

public class LeaderBoardRedis {
	/**
	 * TIME-PERIOD-BASED-LEADER-BOARD
	 */
	public static final String DAY = "day";
	public static final String WEEK = "week";
	public static final String MONTH = "month";
	
	
	public static Logger logger = LoggerUtil.getDailyLogger("LeaderboardTopicLevelRedis" + "_error"); 
	
	public static final String TOPIC_USER_LEVEL_SORTED_KEY = "quizup_topic:topic_xp_sorted:"; // + topicId
	public static final String CURRENT_DAY = "current_day";
	public static final String CURRENT_WEEK = "current_week";
	public static final String CURRENT_MONTH = "current_month";
	public static final String CURRENT_TIME = "current_time";
	
	
	public String getTopicLevelSortedlKey(String topicId, String timePeriod) {
		return TOPIC_USER_LEVEL_SORTED_KEY + topicId + ":" + timePeriod;
	}
	
	



	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-SORTED-LIST IN EACH TOPIC RANKING
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addUserScoreDay(int xpScore, String topicId, String userId) {
		String day = ComUtils.day(new Date());
		this.addUserScoreInTopic(xpScore, topicId, userId, DAY + ":" + day);
	}
	
	public void addUserScoreWeek(int xpScore, String topicId, String userId) {
		String week = ComUtils.week(new Date());
		this.addUserScoreInTopic(xpScore, topicId, userId, WEEK + ":" + week);
	}
	
	public void addUserScoreMonth(int xpScore, String topicId, String userId) {
		String month = ComUtils.month(new Date());
		this.addUserScoreInTopic(xpScore, topicId, userId, MONTH + ":" + month);
	}
	
	public void addUserScoreInTopic(int xpScore, String topicId, String userId, String timePeriod) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zincrby(this.getTopicLevelSortedlKey(topicId, timePeriod), xpScore, userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}	
	
	public void updateUserScoreDay(int xpScore, String topicId, String userId) {
		this.updateUserScoreInTopic(xpScore, topicId, userId, DAY);
	}
	public void updateUserScoreWeek(int xpScore, String topicId, String userId) {
		this.updateUserScoreInTopic(xpScore, topicId, userId, WEEK);
	}
	public void updateUserScoreMonth(int xpScore, String topicId, String userId) {
		this.updateUserScoreInTopic(xpScore, topicId, userId, MONTH);
	}
	
	public void updateUserScoreInTopic(int xpScore, String topicId, String userId, String timePeriod) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getTopicLevelSortedlKey(topicId, timePeriod), xpScore, userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public Double getUserScoreDay(int xpScore, String topicId, String userId) {
		return this.getUserScoreInTopic(topicId, userId, DAY);
	}
	public Double getUserScoreWeek(int xpScore, String topicId, String userId) {
		return this.getUserScoreInTopic(topicId, userId, WEEK);
	}
	public Double getUserScoreMonth(int xpScore, String topicId, String userId) {
		return this.getUserScoreInTopic(topicId, userId, MONTH);
	}
	
	public Double getUserScoreInTopic(String topicId, String userId, String timePeriod) {
		Jedis jedis = RedisPool.getJedis();
		try {
			Double score = jedis.zscore(this.getTopicLevelSortedlKey(topicId, timePeriod), userId);
			return score;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return 0.0;
	}
	
	public void removeUserScoreDay(String topicId, String userId) {
		this.removeUserScoreTopic(topicId, userId, DAY);
	}
	public void removeUserScoreWeek(String topicId, String userId) {
		this.removeUserScoreTopic(topicId, userId, WEEK);
	}
	public void removeUserScoreMonth(String topicId, String userId) {
		this.removeUserScoreTopic(topicId, userId, MONTH);
	}
	
	public void removeUserScoreTopic(String topicId, String userId, String timePeriod) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zrem(this.getTopicLevelSortedlKey(topicId, timePeriod), userId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void resetUserScoreDay(String topicId) {
		this.resetUserScoreTopic(topicId, DAY);
	}
	public void resetUserScoreWeek(String topicId) {
		this.resetUserScoreTopic(topicId, WEEK);
	}
	public void resetUserScoreMonth(String topicId) {
		this.resetUserScoreTopic(topicId, MONTH);
	}
	
	public void resetUserScoreTopic(String topicId, String timePeriod) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.del(this.getTopicLevelSortedlKey(topicId, timePeriod));
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public long getUserScoreRankDay(String topicId, String userId) {
		return this.getUserScoreRank(topicId, userId, DAY);
	}
	public long getUserScoreRankWeek(String topicId, String userId) {
		return this.getUserScoreRank(topicId, userId, WEEK);
	}
	public long getUserScoreRankMonth(String topicId, String userId) {
		return this.getUserScoreRank(topicId, userId, MONTH);
	}
	
	public long getUserScoreRank(String topicId, String userId, String timePeriod) {
		long rank = -1;
		Jedis jedis = RedisPool.getJedis();
		try {
			rank = 1 + jedis.zrevrank(this.getTopicLevelSortedlKey(topicId, timePeriod), userId);
			return rank;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return rank;
	}
	
	
	public List<String> getUserListDay(String topicId, long startIndex, long endIndex) {
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, DAY);
	}
	public List<String> getUserListWeek(String topicId, long startIndex, long endIndex) {
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, WEEK);
	}
	public List<String> getUserListMonth(String topicId, long startIndex, long endIndex) {
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, MONTH);
	}
	
	public List<String> getUserIdListFromTopicRank(String topicId, long startIndex, long endIndex, String timePeriod) {
		/*GET USER FROM REDIS*/
		List<String> userLevelList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			Set<String> userSet = new HashSet<String>();
			userSet = jedis.zrevrange(this.getTopicLevelSortedlKey(topicId, timePeriod), startIndex, endIndex);
			
			userLevelList.addAll(userSet);
			return userLevelList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return userLevelList;
	}



}
