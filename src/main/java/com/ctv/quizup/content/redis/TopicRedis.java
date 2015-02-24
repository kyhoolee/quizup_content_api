package com.ctv.quizup.content.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class TopicRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("TopicRedis" + "_error");
	
	public static final String PUBLISHED_ROOT_TOPIC_SET = "quizup_content:root_topic_published";
	public static final String PUBLISHED_SUB_TOPIC_SET = "quizup_content:sub_topic_published";
	
	
	public static final String TOPIC_ROOT_SET = "quizup_content:root_topic_set";
	public static final String TOPIC_HASHES = "quizup_content:topic_hashes";
	public static final String SUB_TOPIC_HASHES = "quizup_content:sub_topic_hashes:"; // + parentId
	
	public static final String TOPIC_RANKING = "quizup_content:topic_ranking:"; // + topic_rank_type

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-PUBLISH
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isPubRootTopic(String topicId) {
		return this.isPubTopic(topicId, 0);
	}
	public boolean isPubSubTopic(String topicId) {
		return this.isPubTopic(topicId, 1);
	}
	
	
	
	public boolean isPubTopic(String topicId, int type) {
		Jedis jedis = RedisPool.getJedis();
		boolean result = false;
		try {
			if(type == 0) {
				result = jedis.sismember(PUBLISHED_ROOT_TOPIC_SET, topicId);
				//logger.info("topicId:" + topicId + " -- " + result + " -- type:" + type);
				return result;
			} else if(type == 1) {
				result = jedis.sismember(PUBLISHED_SUB_TOPIC_SET, topicId);
				//logger.info("topicId:" + topicId + " -- " + result + " -- type:" + type);
				return result;
			}
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return false;
	}
	
	public void updatePubRootList(List<String> topicList) {
		this.updatePubList(topicList, 0);
	}
	public void updatePubSubList(List<String> topicList) {
		this.updatePubList(topicList, 1);
	}
	
	public void updatePubList(List<String> topicList, int type) {
		for(String topicId : topicList) {
			this.updatePublishedTopic(topicId, type);
		}
	}
	
	public void updatePubRootTopic(String topicId) {
		this.updatePublishedTopic(topicId, 0);
	}
	
	public void updatePubSubTopic(String topicId) {
		this.updatePublishedTopic(topicId, 1); 
	}
	
	public void updatePublishedTopic(String topicId, int type) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			if(type == 0) {
				jedis.sadd(PUBLISHED_ROOT_TOPIC_SET, topicId);
			} else if(type == 1) {
				jedis.sadd(PUBLISHED_SUB_TOPIC_SET, topicId);
			}
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-BUSINESS
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getSubTopicHashes(String topicId) {
		return SUB_TOPIC_HASHES + topicId;
	}
	
	public String getTopicRankKey(String rankType) {
		return TOPIC_RANKING + rankType;
	}
	public String getTopicHotRankKey() {
		return this.getTopicRankKey(ContentRankType.TOPIC_HOT_RANKING);
	}
	public String getTopicNewRankKey() {
		return this.getTopicRankKey(ContentRankType.TOPIC_NEW_RANKING);
	}
	
	
	/**
	 * Update topic-hot-score list
	 * @param topicScore
	 */
	public void updateHotTopicList(Map<String, Double> topicScore) {
		for(String topicId : topicScore.keySet()) {
			double score = topicScore.get(topicId);
			this.updateTopicHot(topicId, score);
		}
	}
	
	public void updateTopicHot(String topicId, double score) {
		this.updateTopicScoreRanking(topicId, score, ContentRankType.TOPIC_HOT_RANKING);
	}
	
	/**
	 * Update topic-new-score list
	 * @param topicList
	 */
	public void updateNewTopicList(List<Topic> topicList) {
		for(Topic topic : topicList) {
			this.updateTopicNew(topic.getTopicId(), topic.getModifiedDate().getTime());
		}
	}
	
	
	public void updateTopicNew(String topicId, long timeStamp) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			jedis.zadd(this.getTopicRankKey(ContentRankType.TOPIC_NEW_RANKING), timeStamp, topicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void removeTopicNew(String topicId) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			jedis.zrem(this.getTopicRankKey(ContentRankType.TOPIC_NEW_RANKING), topicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public List<Topic> getTopicHot(long start, long size) {
		List<Topic> topicList = this.getTopicByRankScore(ContentRankType.TOPIC_HOT_RANKING, start, size);
		
		return topicList;
	}
	public List<Topic> getTopicNew(long start, long size) {
		List<Topic> topicList = this.getTopicByRankScore(ContentRankType.TOPIC_NEW_RANKING, start, size);
		
		return topicList;
	}
	
	/**
	 * Increase topic-ranking value
	 * @param topicId
	 * @param value
	 * @param rankType
	 */
	public void updateTopicIncCount(String topicId, double value, String rankType) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			jedis.zincrby(this.getTopicRankKey(rankType), value, topicId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	/**
	 * Update topic-ranking value
	 * @param topicId
	 * @param score
	 * @param rankType
	 */
	public void updateTopicScoreRanking(String topicId, double score, String rankType) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			jedis.zadd(this.getTopicRankKey(rankType), score, topicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void removeUnpubTopic(String rankType) {
		Jedis jedis = RedisPool.getJedis();
		try {
			long start = 0;
			long end = -1;
			Set<String> topicIdList = jedis.zrevrange(this.getTopicRankKey(rankType), start, end);
			
			for(String topic : topicIdList) {
				if(!this.isPubSubTopic(topic)) {
					jedis.zrem(this.getTopicRankKey(rankType), topic);
				}
			}
			
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//System.out.println(e.toString());
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public List<Topic> getTopicByRankScore(String rankType, long start, long size) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		Jedis jedis = RedisPool.getJedis();
		try {
			
			long end = start + size - 1;
			if(size == -1) {
				start = 0;
				end = -1;
			}
			
			Set<String> topicIdList = jedis.zrevrange(this.getTopicRankKey(rankType), start, end);
			
			for(String topicId : topicIdList) {
				if(this.isPubSubTopic(topicId)) {
					Topic topic = this.getTopicById(topicId);
					if(topic != null) {
						topicList.add(topic);
					}
				}
			}
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//System.out.println(e.toString());
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return topicList;
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-CRUD
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void writeTopicToRedis(String topicJSON, String topicId) {
		Jedis jedis = RedisPool.getJedis();
		
		try {
			jedis.hset(TOPIC_HASHES, topicId, topicJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void writeSubTopicHashes(String parentId, String subId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getSubTopicHashes(parentId), subId, subId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//e.printStackTrace();
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	
	

	public void writeRootTopicToRedis(String rootTopicId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.sadd(TOPIC_ROOT_SET, rootTopicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public List<Topic> getAllRootTopic() {
		List<Topic> topicList = new ArrayList<Topic>();
		
		/*READ ROOT-TOPIC-SET FROM REDIS*/
		Set<String> list = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			list = jedis.smembers(TOPIC_ROOT_SET);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//e.printStackTrace();
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ROOT-TOPIC FROM JSON TO JAVA-OBJECT*/	
		for (String topicId : list) {
			try {
				if(this.isPubRootTopic(topicId)) {
					Topic topic = this.getTopicById(topicId);
					topicList.add(topic);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return topicList;
	}
	
	public Topic getTopicById(String topicId) {
		String topicJSON = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			topicJSON = jedis.hget(TOPIC_HASHES, topicId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				Topic topic = mapper.readValue(topicJSON, Topic.class);
				return topic;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		return null;
	}
	
	public List<Topic> getSubTopic(String topicId) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		/*READ ROOT-TOPIC-SET FROM REDIS*/
		Set<String> list = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			list = jedis.hkeys(this.getSubTopicHashes(topicId));
			//logger.info(list);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ROOT-TOPIC FROM JSON TO JAVA-OBJECT*/	
		for (String subId : list) {
			try {
				if(this.isPubSubTopic(subId)) {
					Topic topic = this.getTopicById(subId);
					//logger.info("topic:" + topic.toString());
					if(topic != null) {
						topicList.add(topic);
					}
				}
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
			}
		}
		
		return topicList;
		
	}

	
	public static void main(String[] args) {
		List<Topic> topicList = new TopicRedis().getAllRootTopic();
		
		for(Topic topic : topicList) {
			System.out.println(topic.toString());
		}
	}

}
