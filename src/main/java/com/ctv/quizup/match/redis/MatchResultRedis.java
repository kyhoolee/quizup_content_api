package com.ctv.quizup.match.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class MatchResultRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("MatchResultRedis"
			+ "_error");
	public static final String MATCH_RESULT_HASHES = "quizup_match_result:match_hashes";

	public static final String USER_MATCH_RESULT_SORTED_SET = "quizup_match_result:user_match_sorted:"; // +
																										// userId;
	public static final String USER_MATCH_RESULT_TOPIC_HASHES = "quizup_match_result:user_match_topic:"; // +
																											// userId;
	public static final String USER_SCORE_HASHES = "quizup_match_score:user_score:"; // +
																						// firstId
																						// +
																						// ":"
																						// +
																						// secondId

	public static final String USER_VERSUS_SCORE_HASHES = "quizup_match_score:user_versus_score";

	public String getMatchResultKey() {
		return MATCH_RESULT_HASHES;
	}
	public String getMatchResultKey(String topicId) {
		return MATCH_RESULT_HASHES + ":" + topicId;
	}

	public String getUserMatchResultSortedKey(String userId) {
		return USER_MATCH_RESULT_SORTED_SET + userId;
	}

	public String getUserTopicMatchResultKey(String userId) {
		return USER_MATCH_RESULT_TOPIC_HASHES + userId;
	}

	public String getUserScoreKey(String firstId, String secondId) {
		return USER_SCORE_HASHES + firstId + ":" + secondId;
	}

	public String getUserVersusScoreKey() {
		return USER_VERSUS_SCORE_HASHES;
	}
	public String getUserVersusScoreKey(String topicId) {
		return USER_VERSUS_SCORE_HASHES + ":" + topicId;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// CRUD USER-SCORE
	///////////////////////////////////////////////////////////////////////////////////////////////////	

	public void updateUserScore(MatchProcess.Score score, String firstId,
			String secondId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserVersusScoreKey(), firstId + ":" + secondId,
					score.toString());

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}
	public void updateUserTopicScore(MatchProcess.Score score, String topicId, String firstId,
			String secondId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserVersusScoreKey(topicId), firstId + ":" + secondId,
					score.toString());

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public MatchProcess.Score getUserScore(String firstId, String secondId) {
		MatchProcess.Score match = new MatchProcess.Score();

		/* GET SCORE FROM REDIS */
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(this.getUserVersusScoreKey(), 
					firstId + ":" + secondId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE SCORE FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();

		try {
			match = mapper.readValue(matchJSON, MatchProcess.Score.class);
		} catch (Exception e) {
			logger.error(e);
			// System.out.println(e.toString());
		}

		return match;
	}
	
	public MatchProcess.Score getUserTopicScore(String topicId, String firstId, String secondId) {
		MatchProcess.Score match = new MatchProcess.Score();

		/* GET SCORE FROM REDIS */
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(this.getUserVersusScoreKey(topicId), 
					firstId + ":" + secondId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE SCORE FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();

		try {
			match = mapper.readValue(matchJSON, MatchProcess.Score.class);
		} catch (Exception e) {
			logger.error(e);
			// System.out.println(e.toString());
		}

		return match;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// CRUD MATCH-RESULT
	///////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public void writeUserTopicMatchResult(String userTopicMatchResult,
			String topicId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserTopicMatchResultKey(userId), topicId,
					userTopicMatchResult);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public List<String> getUserTopicMatchResult(String userId) {
		List<String> userTopicMatch = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> topicSet = jedis.hkeys(this
					.getUserTopicMatchResultKey(userId));
			userTopicMatch.addAll(topicSet);

			return userTopicMatch;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		return userTopicMatch;
	}

	public void writeUserMatchResultSorted(String matchId, String userId,
			long timeStamp) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getUserMatchResultSortedKey(userId), timeStamp,
					matchId);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public void updateMatchResult(String matchJSON, String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getMatchResultKey(), matchId, matchJSON);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public MatchResult getMatchResultById(String matchId) {
		MatchResult match = null;

		/* GET USER FROM REDIS */
		String matchJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			matchJSON = jedis.hget(this.getMatchResultKey(), matchId);
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
			match = mapper.readValue(matchJSON, MatchResult.class);
		} catch (Exception e) {
			logger.error(e);
			// System.out.println(e.toString());
		}

		return match;

	}

	public List<MatchResult> getUserMatchResultListByIndex(String userId,
			int startIndex, int endIndex) {
		List<MatchResult> userMatch = new ArrayList<MatchResult>();

		/* GET ACTIVTY FROM REDIS */
		Set<String> userMatchList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userMatchList = jedis.zrevrange(
					this.getUserMatchResultSortedKey(userId), startIndex,
					endIndex);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE ACTIVTY FROM JSON TO JAVA-OBJECT */
		for (String matchId : userMatchList) {
			try {
				MatchResult uResult = this.getMatchResultById(matchId);
				userMatch.add(uResult);
			} catch (Exception e) {
				logger.error(e);
				// System.out.println(e.toString());
			}
		}

		return userMatch;
	}

	public List<MatchResult> getUserMatchListByTime(String topicId,
			String userId, long startTime, long endTime) {
		List<MatchResult> userMatch = new ArrayList<MatchResult>();

		/* GET ACTIVTY FROM REDIS */
		Set<String> userMatchList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userMatchList = jedis.zrevrangeByScore(
					this.getUserMatchResultSortedKey(userId), endTime,
					startTime);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE ACTIVTY FROM JSON TO JAVA-OBJECT */
		for (String matchId : userMatchList) {
			try {
				MatchResult uResult = this.getMatchResultById(matchId);
				userMatch.add(uResult);
			} catch (Exception e) {
				logger.error(e);
				// System.out.println(e.toString());
			}
		}

		return userMatch;
	}

	public void removeMatchResult(String matchId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(this.getMatchResultKey(), matchId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

	}
}
