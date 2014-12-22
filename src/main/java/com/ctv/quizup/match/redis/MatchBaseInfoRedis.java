package com.ctv.quizup.match.redis;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MatchBaseInfoRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("MatchBaseInfoRedis" + "_error");
	
	public static final String MATCH_BASE_INFO_HASHES = "quizup_match:match_base_info_hashes"; 
	
	public void removeMatchBase(String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(MATCH_BASE_INFO_HASHES, matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void updateMatchBaseInfo(String matchJSON, String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(MATCH_BASE_INFO_HASHES, matchId, matchJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public MatchBaseInfo getMatchBaseInfoById(String matchId) {
		MatchBaseInfo match = null;
		
		/*GET USER FROM REDIS*/
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(MATCH_BASE_INFO_HASHES, matchId);
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
				match = mapper.readValue(matchJSON, MatchBaseInfo.class);
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		
		
		return match;
		
	}
}
