package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.ComUtils;
import com.ctv.quizup.util.LoggerUtil;

public class CurrentBoardRedis {
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
	
	
	public String getDayTopicSortedKey(String topicId) {
		return this.getTopicLevelSortedlKey(topicId, DAY);
	}
	public String getWeekTopicSortedKey(String topicId) {
		return this.getTopicLevelSortedlKey(topicId, WEEK);
	}
	public String getMonthTopicSortedKey(String topicId) {
		return this.getTopicLevelSortedlKey(topicId, MONTH);
	}
	
	public void setCurrentTimeDay(String topicId) {
		this.setCurrentTime(topicId  + ":" + DAY);
	}
	public void setCurrentTimeWeek(String topicId) {
		this.setCurrentTime(topicId  + ":" + WEEK);
	}
	public void setCurrentTimeMonth(String topicId) {
		this.setCurrentTime(topicId  + ":" + MONTH);
	}
	
	public void setCurrentTime(String topicId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			Date current = new Date();
			jedis.set(CURRENT_TIME + ":" + topicId, current.getTime() + "");
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public Date getCurrentTimeDay(String topicId) {
		return this.getCurrentTime(topicId + ":" + DAY);
	}
	public Date getCurrentTimeWeek(String topicId) {
		return this.getCurrentTime(topicId + ":" + WEEK);
	}
	public Date getCurrentTimeMonth(String topicId) {
		return this.getCurrentTime(topicId + ":" + MONTH);
	}
	
	public Date getCurrentTime(String topicId) {
		Date current = null;
		Jedis jedis = RedisPool.getJedis();
		try {
			
			long c = Long.parseLong(jedis.get(CURRENT_TIME + ":" + topicId));
			current = new Date(c);
			return current;
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return current;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// CHECK-&-RESET USER-SORTED-LIST IN EACH TOPIC RANKING
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void checkResetDay(String topicId) {
		Date store = this.getCurrentTimeDay(topicId);
		
		if(store == null){
			this.resetUserScoreDay(topicId);
			this.setCurrentTimeDay(topicId);
		} else {
			String storeDay = ComUtils.day(store);
			String day = ComUtils.day(new Date());
			
			if(!day.equalsIgnoreCase(storeDay)) {
				this.resetUserScoreDay(topicId);
				this.setCurrentTimeDay(topicId);
			} 
		}
	}
	
	public void checkResetWeek(String topicId) {
		Date store = this.getCurrentTimeWeek(topicId);
		
		if(store == null){
			this.resetUserScoreWeek(topicId);
			this.setCurrentTimeWeek(topicId);
		} else {
			String storeWeek = ComUtils.week(store);
			String week = ComUtils.week(new Date());
			
			if(!week.equalsIgnoreCase(storeWeek)) {
				this.resetUserScoreWeek(topicId);
				this.setCurrentTimeWeek(topicId);
			} 
		}
	}
	
	public void checkResetMonth(String topicId) {
		Date store = this.getCurrentTimeMonth(topicId);
		
		if(store == null){
			this.resetUserScoreMonth(topicId);
			this.setCurrentTimeMonth(topicId);
		} else {
			String storeMonth = ComUtils.month(store);
			String month = ComUtils.month(new Date());
			
			if(!month.equalsIgnoreCase(storeMonth)) {
				this.resetUserScoreMonth(topicId);
				this.setCurrentTimeMonth(topicId);
			} 
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-SORTED-LIST IN EACH TOPIC RANKING
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addUserScoreDay(int xpScore, String topicId, String userId) {
		
		this.checkResetDay(topicId);
		this.addUserScoreInTopic(xpScore, topicId, userId, DAY);
		
	}
	
	public void addUserScoreWeek(int xpScore, String topicId, String userId) {
		
		this.checkResetWeek(topicId);
		this.addUserScoreInTopic(xpScore, topicId, userId, WEEK);
		
	}
	
	public void addUserScoreMonth(int xpScore, String topicId, String userId) {
		
		this.checkResetMonth(topicId);
		this.addUserScoreInTopic(xpScore, topicId, userId, MONTH);
		
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
		this.checkResetDay(topicId);
		return this.getUserScoreInTopic(topicId, userId, DAY);
	}
	public Double getUserScoreWeek(int xpScore, String topicId, String userId) {
		this.checkResetWeek(topicId);
		return this.getUserScoreInTopic(topicId, userId, WEEK);
	}
	public Double getUserScoreMonth(int xpScore, String topicId, String userId) {
		this.checkResetMonth(topicId);
		return this.getUserScoreInTopic(topicId, userId, MONTH);
	}
	
	public void checkReset(String topicId, String timePeriod) {
		if(timePeriod == DAY) {
			this.checkResetDay(topicId);
		} else if (timePeriod == WEEK) {
			this.checkResetWeek(topicId);
		} else if (timePeriod == MONTH) {
			this.checkResetMonth(topicId);
		}
	}
	

	
	public Double getUserScoreInTopic(String topicId, String userId, String timePeriod) {
		
		
		Jedis jedis = RedisPool.getJedis();
		try {
			Double score = jedis.zscore(this.getTopicLevelSortedlKey(topicId, timePeriod), userId);
			if(score == null) {
				return 0.0;
			}
			
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
		this.checkResetDay(topicId);
		return this.getUserScoreRank(topicId, userId, DAY);
	}
	public long getUserScoreRankWeek(String topicId, String userId) {
		this.checkResetWeek(topicId);
		return this.getUserScoreRank(topicId, userId, WEEK);
	}
	public long getUserScoreRankMonth(String topicId, String userId) {
		this.checkResetMonth(topicId);
		return this.getUserScoreRank(topicId, userId, MONTH);
	}
	
	public long getUserScoreRank(String topicId, String userId, String timePeriod) {
		this.checkReset(topicId, timePeriod);
		
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
		this.checkResetDay(topicId);
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, DAY);
	}
	public List<String> getUserListWeek(String topicId, long startIndex, long endIndex) {
		this.checkResetWeek(topicId);
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, WEEK);
	}
	public List<String> getUserListMonth(String topicId, long startIndex, long endIndex) {
		this.checkResetMonth(topicId);
		return this.getUserIdListFromTopicRank(topicId, startIndex, endIndex, MONTH);
	}
	
	public List<String> getUserIdListFromTopicRank(String topicId, long startIndex, long endIndex, String timePeriod) {
		this.checkReset(topicId, timePeriod);
		
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
