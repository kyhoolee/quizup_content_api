package com.ctv.quizup.comm.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.comm.model.Challenge;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class ChallengeRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("ChallengeRedis" + "_error");
	
	public static final String USER_CHALLENGE_HASHES_KEY = "quizup_user_comm:user_challenge_hashes";
	public static final String USER_CHALLENGE_SENDER_KEY = "quizup_user_comm:user_challenge_sender:"; //userId:status
	public static final String USER_CHALLENGE_RECEIVER_KEY = "quizup_user_comm:user_challenge_receiver:"; //userId:status


	public String getChallengeKey() {
		return USER_CHALLENGE_HASHES_KEY ;
	}

	public String getUserSenderKey(String userId, String status) {
		return USER_CHALLENGE_SENDER_KEY + userId + ":" + status;
	}

	public String getUserReceiverKey(String userId, String status) {
		return USER_CHALLENGE_RECEIVER_KEY + userId + ":" + status;
	}



	public void updateChallenge(String challengeJSON, String challengeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getChallengeKey(), challengeId, challengeJSON);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
		}
	}
	


	public void removeChallenge(String challengeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(this.getChallengeKey(), challengeId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
		}
	}

	public void updateChallengeStatus(String challengeId, String oldStatus,
			String newStatus) {

		Challenge challenge = this.getChallengeById(challengeId);
		challenge.setStatus(newStatus);
		this.removeChallenge(challengeId);
		this.updateChallenge(challenge.toString(), challengeId);

	}

	public Challenge getChallengeById(String challengeId) {
		Challenge challenge = null;
		/* GET USER FROM REDIS */
		String uChallenge = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			uChallenge = jedis.hget(this.getChallengeKey(), challengeId);
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
			challenge = mapper.readValue(uChallenge, Challenge.class);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return challenge;
	}
	
	public void updateSenderChallenge(String challengeId, String userId, String oldStatus, String newStatus) {
		this.updateUserChallenge(challengeId, userId, oldStatus, newStatus, "sender");
	}
	public void updateReceiverChallenge(String challengeId, String userId, String oldStatus, String newStatus) {
		this.updateUserChallenge(challengeId, userId, oldStatus, newStatus, "receiver");
	}
	
	public void updateUserChallenge(String challengeId, String userId, String oldStatus, String newStatus, String type) {
		this.updateChallengeStatus(challengeId, oldStatus, newStatus);
		
		this.deleteUserChallenge(challengeId, userId, oldStatus, type);
		this.writeUserChallenge(challengeId, userId, newStatus, type);
	}
	
	public List<Challenge> getSenderChallenge(String userId, String status) {
		return this.getUserChallenge(userId, status, "sender");
	}
	public List<Challenge> getReceiverChallenge(String userId, String status) {
		return this.getUserChallenge(userId, status, "receiver");
	}
	
	public void writeSenderChallenge(String challengeId, String userId, String status) {
		this.writeUserChallenge(challengeId, userId, status, "sender");
	}
	
	public void writeReceiverChallenge(String challengeId, String userId, String status) {
		this.writeUserChallenge(challengeId, userId, status, "receiver");
	}
	
	public void writeUserChallenge(String challengeId, String userId, String status, String type) {
		Jedis jedis = RedisPool.getJedis();
		try {
			if(type.equalsIgnoreCase("sender")){
				jedis.hset(this.getUserSenderKey(userId, status), challengeId, challengeId);
			} else if(type.equalsIgnoreCase("receiver")) {
				jedis.hset(this.getUserReceiverKey(userId, status), challengeId, challengeId);
			}
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
		}
	}
	
	public void deleteSenderChallenge(String challengeId, String userId, String status) {
		this.deleteUserChallenge(challengeId, userId, status, "sender");
	}
	public void deleteReceiverChallenge(String challengeId, String userId, String status) {
		this.deleteUserChallenge(challengeId, userId, status, "receiver");
	}
	public void deleteUserChallenge(String challengeId, String userId, String status) {
		this.deleteUserChallenge(challengeId, userId, status, "sender");
		this.deleteUserChallenge(challengeId, userId, status, "receiver");
	}
	
	
	public void deleteUserChallenge(String challengeId, String userId, String status, String type) {
		Jedis jedis = RedisPool.getJedis();
		try {
			if(type.equalsIgnoreCase("sender")){
				jedis.hdel(this.getUserSenderKey(userId, status), challengeId, challengeId);
			} else if(type.equalsIgnoreCase("receiver")) {
				jedis.hdel(this.getUserReceiverKey(userId, status), challengeId, challengeId);
			}
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
		}
	}

	public List<Challenge> getUserChallenge(String userId, String status, String type) {
		List<Challenge> challengeList = new ArrayList<Challenge>();

		/* GET CHALLENGE FROM REDIS */
		List<String> userChallList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			if(type.equalsIgnoreCase("sender")) {
				userChallList = jedis.hvals(this.getUserSenderKey(userId, status));
			} else if (type.equalsIgnoreCase("receiver")) {
				userChallList = jedis.hvals(this.getUserReceiverKey(userId, status));
			}
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE ACTIVTY FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();
		for (String challeId : userChallList) {
			try {
				Challenge uChal = this.getChallengeById(challeId);
				if(uChal != null) {
					challengeList.add(uChal);
				}
//				} else {
//					this.deleteUserChallenge(challeId, userId, status, type);
//				}
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		}

		return challengeList;

	}


}
