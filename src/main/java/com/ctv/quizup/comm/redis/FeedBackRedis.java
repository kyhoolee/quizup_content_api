package com.ctv.quizup.comm.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.comm.model.FeedBack;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class FeedBackRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("FeedBackRedis" + "_error");
	
	
	public static final String FEED_BACK_SORTED_KEY = "quizup_user_comm:feed_back_sorted:"; // feedback_type
	public static final String FEED_BACK_HASHES = "quizup_user_comm:feed_back_hashes"; 
	
	public String getFeedBackSorted(String feedbackType) {
		return FEED_BACK_SORTED_KEY + feedbackType;
	}

	public String getFeedBackHashes() {
		return FEED_BACK_HASHES;
	}
	
	public void writeFeedBackToHashes(String feedId, String feedContent) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getFeedBackHashes(), feedId, feedContent);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void writeFeedBackToSorted(long timeStamp, String feedId, String targetType) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getFeedBackSorted(targetType), timeStamp, feedId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public FeedBack getFeedBack(String feedId) {
		FeedBack feed = null;
		
		Jedis jedis = RedisPool.getJedis();
		String feedJSON = "";
		try {
			feedJSON = jedis.hget(this.getFeedBackHashes(), feedId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		/*PARSE FEEDBACK FROM JSON TO JAVA-OBJECT*/		
		ObjectMapper mapper = new ObjectMapper(); 
		
			try {
				feed = mapper.readValue(feedJSON, FeedBack.class);
				
			} catch (Exception e) {
				logger.error(e);
			}
		
		
		return feed;
	}
	
	public List<FeedBack> getTargetFeedBack(String targetType, int start, int end) {
		List<FeedBack> feedList = new ArrayList<FeedBack>();
		
		/*GET FEEDBACK FROM REDIS*/
		Set<String> feedIdList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			feedIdList = jedis.zrevrange(this.getFeedBackSorted(targetType), start, end);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		for(String feedId : feedIdList) {
			try {
				FeedBack feed = this.getFeedBack(feedId);
				feedList.add(feed);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
		return feedList;
	}
	
	
}
