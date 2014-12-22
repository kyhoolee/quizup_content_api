package com.ctv.quizup.comm.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.comm.model.Activity;
import com.ctv.quizup.redis.RedisPool;

public class ActivityRedis {
	public static final String USER_ACTIVITY_HASHES_KEY = "quizup_user_comm:user_activity_hashes:"; 
	public static final String USER_ACTIVITY_SORTED_KEY = "quizup_user_comm:user_activity_sorted:"; // + userId

	public String getUserActivityKey() {
		return USER_ACTIVITY_HASHES_KEY;
	}
	
	public String getUserActSortedKey(String userId) {
		return USER_ACTIVITY_SORTED_KEY + userId;
	}
	
	public void writeActivityToSorted(long timeStamp, String activityId, String userId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getUserActSortedKey(userId), timeStamp, activityId);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeActivityToHashes(String userActivityJSON, String activityId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getUserActivityKey(), activityId, userActivityJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	
	
	public Activity getActivity(String activityId) {
		Activity userActivity = new Activity();
		
		/*GET USER FROM REDIS*/
		String uActivity = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			uActivity = jedis.hget(this.getUserActivityKey(), activityId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE USER FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				userActivity = mapper.readValue(uActivity, Activity.class);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		
		return userActivity;
		
	}


	
	
	public List<Activity> getUserActListByIndex(String userId, long startIndex, long endIndex) {
		List<Activity> userActivity = new ArrayList<Activity>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> userActList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userActList = jedis.zrevrange(this.getUserActSortedKey(userId), startIndex, endIndex);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/	
		for(String userAct : userActList) {
			try {
				Activity uAct = this.getActivity(userAct);
				userActivity.add(uAct);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return userActivity;
	}
	
	public List<Activity> getUserActListByTime(String userId, long startTime, long endTime) {
		List<Activity> userActivity = new ArrayList<Activity>();
		
		/*GET ACTIVTY FROM REDIS*/
		Set<String> userActList = new HashSet<String>();
		Jedis jedis = RedisPool.getJedis();
		try {
			// Get activity from new to old
			userActList = jedis.zrevrangeByScore(this.getUserActSortedKey(userId), endTime, startTime);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE ACTIVTY FROM JSON TO JAVA-OBJECT*/	
		for(String userAct : userActList) {
			try {
				Activity uAct = this.getActivity(userAct);
				userActivity.add(uAct);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return userActivity;
	}

}
