package com.ctv.quizup.match.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MatchBaseInfoRedis {
	public static Logger logger = LoggerUtil
			.getDailyLogger("MatchBaseInfoRedis" + "_error");

	public static final String MATCH_BASE_INFO_HASHES = "quizup_match:match_base_info_hashes";
	public static final String MATCH_SORTED = "quizup_match:match_base_info_sorted";
	
	public List<MatchBaseInfo> removeMatchList(long startTime, long endTime) {
		List<String> matchIds = this.getMatchIdListSorted(startTime, endTime);
		
		List<MatchBaseInfo> matchList = this.getMatchList(matchIds);
		this.removeMatchList(matchIds);
		this.removeMatchIdList(startTime, endTime);
		
		return matchList;
	}
	
	public List<MatchBaseInfo> removeMatchs(long startTime, long endTime) {
		List<String> matchIds = this.getMatchIdListSorted(startTime, endTime);
		
		List<MatchBaseInfo> matchList = this.getMatchList(matchIds);
		//this.removeMatchList(matchIds);
		this.removeMatchIdList(startTime, endTime);
		
		return matchList;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MATCH-SORTED-LIST 
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void updateMatchSorted(String matchId, long timeStamp) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(MATCH_SORTED, timeStamp, matchId);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error("updateUserLevelInTopic:" + e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public void removeMatchSorted(String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zrem(MATCH_SORTED, matchId);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}
	
	public void removeMatchSorted(List<String> matchIds) {
		for(String matchId : matchIds) {
			this.removeMatchSorted(matchId);
		}
	}

	public long getMatchSortedRank(String matchId) {
		long rank = -1;
		Jedis jedis = RedisPool.getJedis();
		try {
			rank = 1 + jedis.zrevrank(MATCH_SORTED, matchId);
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
	
	public void removeMatchIdList(long startTime, long endTime) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zremrangeByScore(MATCH_SORTED, startTime, endTime );
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
		}
	}

	public List<String> getMatchIdListSorted(long startTime, long endTime) {
		/* GET USER FROM REDIS */
		List<String> userLevelList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			Set<String> userSet = new HashSet<String>();
			userSet = jedis.zrevrangeByScore(MATCH_SORTED,startTime, endTime);
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

	// ////////////////////////////////////////////////////////////////////////////////////////
	// MATCH-BASE-INFO CRUD
	// ////////////////////////////////////////////////////////////////////////////////////////

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
	
	public void removeMatchList(List<String> matchIds) {
		for(String matchId : matchIds) {
			this.removeMatchBase(matchId);
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
	
	public List<MatchBaseInfo> getMatchList(List<String> matchIds) {
		List<MatchBaseInfo> matchList = new ArrayList<MatchBaseInfo>();
		
		for(String matchId : matchIds) {
			MatchBaseInfo match = this.getMatchBaseInfoById(matchId);
			matchList.add(match);
		}
		
		return matchList;
	}

	public MatchBaseInfo getMatchBaseInfoById(String matchId) {
		MatchBaseInfo match = null;

		/* GET USER FROM REDIS */
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

		/* PARSE USER FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();

		try {
			match = mapper.readValue(matchJSON, MatchBaseInfo.class);
		} catch (Exception e) {
			logger.error(e);
			// System.out.println(e.toString());
		}

		return match;

	}
}
