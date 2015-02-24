package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.util.LoggerUtil;

public class UserBadgeAchievRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("UserBadgeRedis"
			+ "_error");

	public static final String USER_ACHIEVEMENT_HASHES_KEY = "quizup_user:user_achievement_hashes:"; //727126420669934
	public static final String BADGE_HASHES = "quizup_badge:badge_hashes";

	public static final String BADGE_REFRESH_SET = "quizup_badge:badge_refresh_set:"; 

	public String getUserAchievementKey(String userId) {
		return USER_ACHIEVEMENT_HASHES_KEY + userId;
	}

	public String getUserFreshKey(String userId) {
		return BADGE_REFRESH_SET + userId;
	}

	public void updateUserBadge(BadgeAchiev achiev) {
		this.writeUserBadgeAchievementToRedis(achiev.toString(),
				achiev.getBadgeId(), achiev.getUserId());
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FRESH-ACHIEVEMENT IN EACH USER PROFILE
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void writeFreshAchiev(List<BadgeAchiev> achievList) {
		if(achievList == null)
			return;
		
		for(BadgeAchiev achiev : achievList) {
			if(achiev!= null && achiev.isFinished()) {
				this.writeFreshAchiev(achiev);
			}
		}
	}
	
	public void writeFreshAchiev(BadgeAchiev achiev) {
		this.writeFreshAchiev(achiev.getUserId(), achiev.getBadgeId());
	}

	public void writeFreshAchiev(String userId, String badgeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(this.getUserAchievementKey(userId), badgeId);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public List<BadgeAchiev> getFreshAchiev(String userId) {
		List<BadgeAchiev> userAchievement = new ArrayList<BadgeAchiev>();

		/* GET USER FROM REDIS */
		Set<String> userAchieveList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userAchieveList = jedis.smembers(this.getUserFreshKey(userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		for (String badgeId : userAchieveList) {
			BadgeAchiev uAchiev = this.getUserAchievement(userId, badgeId);
			if (uAchiev != null)
				userAchievement.add(uAchiev);

		}

		return userAchievement;
	}
	
	public void removeFreshAchiev(String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.del(this.getUserFreshKey(userId));

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BADGE-ACHIEVEMENT IN EACH USER PROFILE
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeUserBadgeAchievementToRedis(
			String userBadgeAchievementJSON, String badgeId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserAchievementKey(userId), badgeId,
					userBadgeAchievementJSON);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public BadgeAchiev getUserAchievement(String userId, String badgeId) {
		BadgeAchiev userAchievement = null;

		/* GET USER FROM REDIS */
		String userAchieve = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userAchieve = jedis.hget(this.getUserAchievementKey(userId),
					badgeId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
			return null;
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE USER FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();
		try {
			userAchievement = mapper.readValue(userAchieve,
					BadgeAchiev.class);

		} catch (Exception e) {
			logger.error(e);
			return null;
		}

		return userAchievement;

	}

	public List<BadgeAchiev> getUserAchievementByUserId(String userId) {
		List<BadgeAchiev> userAchievement = new ArrayList<BadgeAchiev>();

		/* GET USER FROM REDIS */
		List<String> userAchieveList = new ArrayList<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			userAchieveList = jedis.hvals(this.getUserAchievementKey(userId));
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE USER FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();
		for (String userAchieve : userAchieveList) {
			try {
				BadgeAchiev uAchiev = mapper.readValue(userAchieve,
						BadgeAchiev.class);
				userAchievement.add(uAchiev);
			} catch (Exception e) {
				logger.error(e);
				// System.out.println(e.toString());
			}
		}

		return userAchievement;

	}
	
	public void removeUserAchievs(String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.del(this.getUserAchievementKey(userId));

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}
}
