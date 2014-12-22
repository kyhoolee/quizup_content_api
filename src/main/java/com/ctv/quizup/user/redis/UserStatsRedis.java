package com.ctv.quizup.user.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.UserPlayStats;

public class UserStatsRedis {
	public static final String STATS_HASHES = "quizup_user:stats_hashes:";
	
	public String getTotalMatchKey() {
		return STATS_HASHES + "total_match";
	}
	public String getWinMatchKey() {
		return STATS_HASHES + "win_match";
	}
	public String getLoseMatchKey() {
		return STATS_HASHES + "lose_match";
	}
	public String getTieMatchKey() {
		return STATS_HASHES + "tie_match";
	}
	
	public String getMatchPointKey() {
		return STATS_HASHES + "match_point";
	}
	public String getAnswerTimeKey() {
		return STATS_HASHES + "answer_time";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// INCREASE-STATS-VALUES
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void incrTotalMatch(String userId) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getTotalMatchKey(), userId, 1);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	public void incrWinMatch(String userId) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getWinMatchKey(), userId, 1);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	public void incrLoseMatch(String userId) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getLoseMatchKey(), userId, 1);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	public void incrTieMatch(String userId) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getTieMatchKey(), userId, 1);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	public void incrMatchPoint(String userId, int addPoint) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getMatchPointKey(), userId, addPoint);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	public void incrAnswerTime(String userId, int addTime) {
		Jedis jedis = RedisPool.getJedis(); 
		try {
			jedis.hincrBy(this.getAnswerTimeKey(), userId, addTime);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// GET-STATS-VALUES
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getTotalMatch(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int totalMatch = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			totalMatch = Integer.parseInt(jedis.hget(this.getTotalMatchKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return totalMatch;
		
	}
	public int getWinMatch(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int winMatch = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			winMatch = Integer.parseInt(jedis.hget(this.getWinMatchKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return winMatch;
		
	}
	public int getLoseMatch(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int loseMatch = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			loseMatch = Integer.parseInt(jedis.hget(this.getLoseMatchKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return loseMatch;
		
	}
	public int getTieMatch(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int tieMatch = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			tieMatch = Integer.parseInt(jedis.hget(this.getTieMatchKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return tieMatch;
		
	}
	public int getMatchPoint(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int matchPoint = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchPoint = Integer.parseInt(jedis.hget(this.getMatchPointKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return matchPoint;
		
	}
	public int getAnswerTime(String userId) {
		
		/*GET Total-Match FROM REDIS*/
		int answerTime = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			answerTime = Integer.parseInt(jedis.hget(this.getAnswerTimeKey(), userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return answerTime;
		
	}
	

	public UserPlayStats getUserPlayStats(String userId) {
		int totalMatch = this.getTotalMatch(userId);
		int winMatch = this.getWinMatch(userId);
		int tieMatch = this.getTieMatch(userId);
		int loseMatch = this.getLoseMatch(userId);
		int matchPoint = this.getMatchPoint(userId);
		int answerTime = this.getAnswerTime(userId);
		int numQues = totalMatch * 7;
		
		
		UserPlayStats stats = new UserPlayStats(totalMatch, 
				winMatch, tieMatch, loseMatch, 
				numQues, matchPoint, answerTime);
		
		
		
		return stats;
	}
}
