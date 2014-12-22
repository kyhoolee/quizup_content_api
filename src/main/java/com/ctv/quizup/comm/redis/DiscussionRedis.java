package com.ctv.quizup.comm.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.comm.model.Discussion;
import com.ctv.quizup.comm.model.DiscussionComment;
import com.ctv.quizup.redis.RedisPool;

public class DiscussionRedis {
	public static final String USER_DISCUSSION_HASHES_KEY = "quizup_user_comm:user_discussion_hashes:"; // + topicId
	public static final String USER_DISCUSSION_SORTED_KEY = "quizup_user_comm:user_discussion_sorted:"; // + topicId
	
	public static final String USER_DISCUSSION_COMMENT_HASHES_KEY = "quizup_user_comm:user_comment_hashes:"; // + discussionId
	public static final String USER_DISCUSSION_COMMENT_SORTED_KEY = "quizup_user_comm:user_comment_sorted:"; // + discussionId
	
	public String getDiscussionKey(String topicId) {
		return USER_DISCUSSION_HASHES_KEY + topicId;
	}
	
	public String getDiscussionSortedKey(String topicId) {
		return USER_DISCUSSION_SORTED_KEY + topicId;
	}
	
	public String getCommentKey(String discussionId) {
		return USER_DISCUSSION_HASHES_KEY + discussionId;
	}
	
	public String getCommentSortedKey(String discussionId) {
		return USER_DISCUSSION_SORTED_KEY + discussionId;
	}
	
	public void writeDiscussionToSortedSet(long timeStamp, String topicId, String discussionId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getDiscussionSortedKey(topicId), timeStamp, discussionId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeDiscussionToRedis(String discussionJSON, String topicId, String discussionId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getDiscussionKey(topicId), discussionId, discussionJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	
	
	public Discussion getTopicDiscussion(String topicId, String discussionId) {
		Discussion discussion = new Discussion();
		
		/*GET DISCUSSION FROM REDIS*/
		String uDiscussion = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			uDiscussion = jedis.hget(this.getDiscussionKey(topicId), discussionId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE DISCUSSION FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				discussion = mapper.readValue(uDiscussion, Discussion.class);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return discussion;
		
	}

	public List<Discussion> getDiscussionListByIndex(String topicId, int start, int end) {
		List<Discussion> topicDiscussion = new ArrayList<Discussion>();
		
		/*GET DISCUSSION FROM REDIS*/
		Set<String> userActList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get discussion from new to old
			userActList = jedis.zrevrange(this.getDiscussionSortedKey(topicId), start, end);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE DISCUSSION FROM JSON TO JAVA-OBJECT*/	
		for(String discussionId : userActList) {
			try {
				Discussion dis = this.getTopicDiscussion(topicId, discussionId);
				topicDiscussion.add(dis);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return topicDiscussion;
	}
	
	
	public List<Discussion> getDiscussionListByTime(String topicId, long startTime, long endTime) {
		List<Discussion> topicDiscussion = new ArrayList<Discussion>();
		
		/*GET DISCUSSION FROM REDIS*/
		Set<String> userActList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get discussion from new to old
			userActList = jedis.zrevrangeByScore(this.getDiscussionSortedKey(topicId), endTime, startTime);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE DISCUSSION FROM JSON TO JAVA-OBJECT*/	
		for(String discussionId : userActList) {
			try {
				Discussion dis = this.getTopicDiscussion(topicId, discussionId);
				topicDiscussion.add(dis);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return topicDiscussion;
	}
	
	
	
	public void writeCommentToSortedSet(long timeStamp, String discussionId, String commentId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getCommentKey(discussionId), timeStamp, commentId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeCommnentToRedis(String commentJSON, String discussionId, String commentId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getCommentSortedKey(discussionId), commentId, commentJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	
	
	public DiscussionComment getDisComment(String discussionId, String commentId) {
		DiscussionComment discussionComment = new DiscussionComment();
		
		/*GET DISCUSSION FROM REDIS*/
		String uDiscussionComment = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			uDiscussionComment = jedis.hget(this.getCommentKey(discussionId), commentId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE DISCUSSION FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				discussionComment = mapper.readValue(uDiscussionComment, DiscussionComment.class);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return discussionComment;
		
	}


	public List<DiscussionComment> getCommentListByIndex(String discussionId, int start, int end) {
		List<DiscussionComment> discussionComment = new ArrayList<DiscussionComment>();
		
		/*GET COMMENT FROM REDIS*/
		Set<String> disComList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get discussion from new to old
			disComList = jedis.zrevrange(this.getCommentSortedKey(discussionId), start, end);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE COMMENT FROM JSON TO JAVA-OBJECT*/	
		for(String commentId : disComList) {
			try {
				DiscussionComment dis = this.getDisComment(commentId, discussionId);
				discussionComment.add(dis);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return discussionComment;
	}
	
	public List<DiscussionComment> getCommentListByTime(String discussionId, long startTime, long endTime) {
		List<DiscussionComment> discussionComment = new ArrayList<DiscussionComment>();
		
		/*GET COMMENT FROM REDIS*/
		Set<String> disComList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get discussion from new to old
			disComList = jedis.zrevrangeByScore(this.getCommentSortedKey(discussionId), endTime, startTime);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE COMMENT FROM JSON TO JAVA-OBJECT*/	
		for(String commentId : disComList) {
			try {
				DiscussionComment dis = this.getDisComment(commentId, discussionId);
				discussionComment.add(dis);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return discussionComment;
	}
}
