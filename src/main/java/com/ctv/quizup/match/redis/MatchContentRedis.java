package com.ctv.quizup.match.redis;

import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.model.MatchContent;
import com.ctv.quizup.redis.RedisPool;

public class MatchContentRedis {
	public static final String MATCH_CONTENT_HASHES = "quizup_match:match_content_hashes"; 
	
	public void removeMatchContent(String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(MATCH_CONTENT_HASHES, matchId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeMatchContentToRedis(String matchJSON, String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(MATCH_CONTENT_HASHES, matchId, matchJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public MatchContent getMatchContentById(String matchId) {
		MatchContent match = null;
		
		/*GET MatchContent FROM REDIS*/
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(MATCH_CONTENT_HASHES, matchId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE MatchContent FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		
			try {
				match = mapper.readValue(matchJSON, MatchContent.class);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return match;
		
	}
}
