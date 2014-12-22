package com.ctv.quizup.user.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;

public class BadgeRedis {
	public static final String BADGE_HASHES = "quizup_badge:badge_hashes";
	public static final String BADGE_COUNT_HASHES = "quizup_badge:badge_count_hashes";
	public static final String BADGE_TYPE = "quizup_badge:badge_type:";

	public void updateBadge(Badge badge) {
		this.writeBadgeToRedis(badge.toString(), badge.getBadgeId());
	}

	public void updateBadgeCount(BadgeCountInfo badgeCount) {
		this.writeBadgeCountToRedis(badgeCount.toString(), badgeCount.getBadgeId());
		this.writeBadgeCountType(badgeCount.getCountType(), badgeCount.getBadgeId());
	}


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BADGE-LIST IN DB
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeBadgeToRedis(String badgeJSON, String badgeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(BADGE_HASHES, badgeId, badgeJSON);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public Badge getBadgeById(String badgeId) {
		Badge badge = null;

		/* GET USER FROM REDIS */
		String badgeJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			badgeJSON = jedis.hget(BADGE_HASHES, badgeId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE USER FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();

		try {
			badge = mapper.readValue(badgeJSON, Badge.class);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return badge;

	}

	public List<Badge> getBadgeList() {
		List<Badge> badgeList = new ArrayList<Badge>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> idSet = jedis.hkeys(BADGE_HASHES);

			for (String badgeId : idSet) {
				Badge badge = this.getBadgeById(badgeId);
				if (badge != null) {
					badgeList.add(badge);
				}
			}

			return badgeList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		return badgeList;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BADGE-COUNT-LIST IN DB
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeBadgeCountToRedis(String badgeJSON, String badgeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(BADGE_COUNT_HASHES, badgeId, badgeJSON);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	public BadgeCountInfo getBadgeCountById(String badgeId) {
		BadgeCountInfo badge = null;

		/* GET USER FROM REDIS */
		String badgeJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			badgeJSON = jedis.hget(BADGE_COUNT_HASHES, badgeId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		/* PARSE USER FROM JSON TO JAVA-OBJECT */
		ObjectMapper mapper = new ObjectMapper();

		try {
			badge = mapper.readValue(badgeJSON, BadgeCountInfo.class);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return badge;

	}

	public List<BadgeCountInfo> getBadgeCountList() {
		List<BadgeCountInfo> badgeList = new ArrayList<BadgeCountInfo>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> idSet = jedis.hkeys(BADGE_COUNT_HASHES);

			for (String badgeId : idSet) {
				BadgeCountInfo badge = this.getBadgeCountById(badgeId);
				if (badge != null) {
					badgeList.add(badge);
				}
			}

			return badgeList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		return badgeList;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BADGE-COUNT-TYPE-LIST IN DB
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getCountTypeKey(CountType type) {
		return BADGE_TYPE + type;
	}
	
	public void writeBadgeCountType(CountType type, String badgeId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getCountTypeKey(type), badgeId, badgeId);

		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}
	}

	

	public List<BadgeCountInfo> getBadgeCountListByType(CountType type) {
		List<BadgeCountInfo> badgeList = new ArrayList<BadgeCountInfo>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> idSet = jedis.hkeys(this.getCountTypeKey(type));

			for (String badgeId : idSet) {
				BadgeCountInfo badge = this.getBadgeCountById(badgeId);
				if (badge != null) {
					badgeList.add(badge);
				}
			}

			return badgeList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		return badgeList;
	}
	
	public List<Badge> getBadgeListByType(CountType type) {
		List<Badge> badgeList = new ArrayList<Badge>();
		Jedis jedis = RedisPool.getJedis();
		try {
			Set<String> idSet = jedis.hkeys(this.getCountTypeKey(type));

			for (String badgeId : idSet) {
				Badge badge = this.getBadgeById(badgeId);
				if (badge != null) {
					badgeList.add(badge);
				}
			}

			return badgeList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);

		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);

		}

		return badgeList;
	}
}
