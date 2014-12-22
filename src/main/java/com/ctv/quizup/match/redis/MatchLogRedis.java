package com.ctv.quizup.match.redis;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.model.MatchContent;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MatchLogRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("MatchLogRedis" + "_error");
	public static final String MATCH_LOG_HASHES = "quizup_match:match_log_hashes:"; 
	
	public String getFirstLogKey() {
		return MATCH_LOG_HASHES + "first";
	}
	public String getSecondLogKey() {
		return MATCH_LOG_HASHES + "second";
	}
	
	public void removeMatchLog(String matchId) {
		this.removeFirstMatchLog(matchId);
		this.removeSecondMatchLog(matchId);
	}
	public void removeFirstMatchLog(String matchId) {
		this.removeMatchLog(matchId, "first");
	}
	public void removeSecondMatchLog(String matchId) {
		this.removeMatchLog(matchId, "second");
	}
	public void removeMatchLog(String matchId, String type) {
		Jedis jedis = RedisPool.getJedis();
		try {
			if(type.equalsIgnoreCase("first")) {
				jedis.hdel(this.getFirstLogKey(), matchId);
			} else if(type.equalsIgnoreCase("second")) {
				jedis.hdel(this.getSecondLogKey(), matchId);
			}
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	

	public void updateFirstMatchLog(MatchLog log, String matchId) {
		this.updateMatchLog(log, matchId, "first");
	}
	public void updateSecondMatchLog(MatchLog log, String matchId) {
		this.updateMatchLog(log, matchId, "second");
	}
	
	public void updateMatchLog(MatchLog log, String matchId, String type) {
		Jedis jedis = RedisPool.getJedis();
		try {
			if(type.equalsIgnoreCase("first")) {
				jedis.hset(this.getFirstLogKey(), matchId, log.toString());
			} else if(type.equalsIgnoreCase("second")) {
				jedis.hset(this.getSecondLogKey(), matchId, log.toString());
			}
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public MatchLog getFirstLogByMatchId(String matchId) {
		return this.getMatchLogById(matchId, "first");
	}
	

	
	
	public MatchLog getSecondLogByMatchId(String matchId) {
		return this.getMatchLogById(matchId, "second");
	}

	public MatchLog getMatchLogById(String matchId, String type) {
		MatchLog match = null;
		
		/*GET MATCH-LOG FROM REDIS*/
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			if(type.equalsIgnoreCase("first")) {
				matchJSON = jedis.hget(this.getFirstLogKey(), matchId);
			} else if(type.equalsIgnoreCase("second")) {
				matchJSON = jedis.hget(this.getSecondLogKey(), matchId);
			}
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE MATCH-LOG FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 		
		
			try {
				match = mapper.readValue(matchJSON, MatchLog.class);
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		
		
		return match;
		
	}
}
