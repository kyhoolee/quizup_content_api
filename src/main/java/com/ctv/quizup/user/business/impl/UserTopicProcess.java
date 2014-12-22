package com.ctv.quizup.user.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.business.ContentProcess.UserTopicFullLevel;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.statistics.TopicLevelCompute;
import com.ctv.quizup.statistics.redis.UserLevelStatRedis;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.user.redis.UserTopicLevelRedis;



public class UserTopicProcess {
	UserTopicLevelRedis topicRedis;
	UserFriendProcess friendProcess;
	
	public UserTopicProcess() {
		this.topicRedis = new UserTopicLevelRedis();
		this.friendProcess = new UserFriendProcess();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-TOPIC-LEVEL-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static class UserTopicFull extends UserTopicLevel {
		private Topic topic;

		public UserTopicFull() {
			super();
		}
		
		public UserTopicFull(Topic topic) {
			super();
			this.topic = topic;
		}
		
		public UserTopicFull(UserTopicLevel userTopic, Topic topic) {
			super(userTopic);
			this.topic = topic;
		}
		
		/**
		 * @return the topic
		 */
		public Topic getTopic() {
			return topic;
		}

		/**
		 * @param topic the topic to set
		 */
		public void setTopic(Topic topic) {
			this.topic = topic;
		}
		
		@Override
		public String toString(){
			String result = "";
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(this);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
	}
	
	public List<UserTopicFullLevel> getUserTopicFullLevelList(String userId, int start, int end) {
		List<UserTopicFullLevel> utList = this.getUserTopicFullLevelList(userId);
		if(end > utList.size())
			end = utList.size();
		utList = utList.subList(start, end);
				
		return utList;
	}
	
	public List<UserTopicFull> getUserTopicFullList(String userId, int start, int end) {
		List<UserTopicFull> utList = this.getUserFullTopicList(userId);
		if(end > utList.size())
			end = utList.size();
		utList = utList.subList(start, end);
				
		return utList;
	}
	
	public List<UserTopicFullLevel> getUserTopicFullLevelList(String userId) {
		List<UserTopicLevel> topicList = new ArrayList<UserTopicLevel>();
		topicList = this.topicRedis.getUserLevelByUserId(userId);
		
		List<UserTopicFullLevel> topicFull = new ArrayList<UserTopicFullLevel>();
		
		TopicRedis redis = new TopicRedis();
		for(UserTopicLevel topicLevel : topicList) {
			Topic topic = redis.getTopicById(topicLevel.getTopicId());
			if(topic != null) {
				UserTopicFullLevel full = new UserTopicFullLevel(topicLevel, topic);
				topicFull.add(full);
			}
			
		}
		
		Collections.sort(topicFull, new Comparator<UserTopicFullLevel>() {

			public int compare(UserTopicFullLevel first, UserTopicFullLevel second) {
				int val1 = first.getTotalXP();
				int val2 = second.getTotalXP();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		
		return topicFull;
	}
	
	public List<UserTopicFull> getUserFullTopicList(String userId) {
		List<UserTopicLevel> topicList = new ArrayList<UserTopicLevel>();
		topicList = this.topicRedis.getUserLevelByUserId(userId);
		
		List<UserTopicFull> topicFull = new ArrayList<UserTopicFull>();
		
		TopicRedis redis = new TopicRedis();
		for(UserTopicLevel topicLevel : topicList) {
			Topic topic = redis.getTopicById(topicLevel.getTopicId());
			if(topic != null) {
				UserTopicFull full = new UserTopicFull(topicLevel, topic);
				topicFull.add(full);
			}
			
		}
		
		Collections.sort(topicFull, new Comparator<UserTopicLevel>() {

			public int compare(UserTopicLevel first, UserTopicLevel second) {
				int val1 = first.getTotalXP();
				int val2 = second.getTotalXP();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		
		return topicFull;
	}
	
	public static void main(String[] args) {
		List<Double> list = new ArrayList<Double>();
		for(int i = 0 ; i < 10 ; i ++) {
			list.add( Math.cos(i * (Math.PI / 7.0) ) );
		}
		
		System.out.println(list);
		
		Collections.sort(list, new Comparator<Double>() {
			public int compare(Double val1, Double val2) {
				if(val1 < val2)
					return 1;
				else if(val1 == val2) 
					return 0;
				else
					return -1;
			}
		});
		
		System.out.println(list);
	}
	
	public List<UserTopicLevel> getUserTopicList(String userId, int start, int end) {
		List<UserTopicLevel> topicList = this.getUserTopicList(userId);
		if(end > topicList.size())
			end = topicList.size();
		topicList = topicList.subList(start, end);
		
		return topicList;
	}
	
	public List<UserTopicLevel> getUserTopicList(String userId) {
		List<UserTopicLevel> topicList = new ArrayList<UserTopicLevel>();
		topicList = this.topicRedis.getUserLevelByUserId(userId);
		
		Collections.sort(topicList, new Comparator<UserTopicLevel>() {

			public int compare(UserTopicLevel first, UserTopicLevel second) {
				int val1 = first.getTotalXP();
				int val2 = second.getTotalXP();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		return topicList;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-USER-LEVEL-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static class TopicUserFull extends UserTopicLevel {
		private UserBaseInfo user;

		public TopicUserFull() {
			super();
		}
		
		public TopicUserFull(UserBaseInfo user) {
			super();
			this.setUser(user);
		}
		
		public TopicUserFull(UserTopicLevel userTopic, UserBaseInfo user) {
			super(userTopic);
			this.user = user;
		}

		/**
		 * @return the user
		 */
		public UserBaseInfo getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(UserBaseInfo user) {
			this.user = user;
		}
		
		@Override
		public String toString(){
			String result = "";
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(this);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
	}
	
	
	public List<TopicUserFull> getTopicUserList(String topicId, long startIndex, long endIndex) {
		List<TopicUserFull> userList = new ArrayList<TopicUserFull>();
		
		List<String> userIdList = this.topicRedis.getUserIdListFromTopicRank(topicId, startIndex, endIndex);
		UserServiceProcess userProcess = new UserServiceProcess();
		for(String userId : userIdList) {
			UserTopicLevel topicLevel = this.topicRedis.getUserTopicLevel(userId, topicId);
			UserBaseInfo userBase = userProcess.getUser(userId);
			TopicUserFull topicUser = new TopicUserFull(topicLevel, userBase);
			
			userList.add(topicUser);
		}
		
		return userList;
	}
	

	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-USER-FRIEND-LEVEL-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<TopicUserFull> getTopicUserFriendList(String topicId, String userId, int startIndex, int endIndex) {
		List<TopicUserFull> userList = this.getTopicUserFriendList(topicId, userId);
		
		if(endIndex == -1) {
			return userList;
		}
		
		userList = userList.subList(startIndex, endIndex);
		
		return userList;
	}
	
	public List<TopicUserFull> getTopicUserFriendList(String topicId, String userId) {
		List<TopicUserFull> userList = new ArrayList<TopicUserFull>();
		List<UserBaseInfo> userBaseList = this.friendProcess.getFriendByService(userId);
		
		for(UserBaseInfo user : userBaseList) {
			UserTopicLevel topicLevel = this.topicRedis.getUserTopicLevel(user.getUserId(), topicId);
			TopicUserFull topicUser = new TopicUserFull(topicLevel, user);
			userList.add(topicUser);
		}
		
		
		Collections.sort(userList, new Comparator<UserTopicLevel>() {

			public int compare(UserTopicLevel first, UserTopicLevel second) {
				int val1 = first.getTotalXP();
				int val2 = second.getTotalXP();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		
		return userList;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public UserTopicLevel getUserTopic(String userId, String topicId) {
		UserTopicLevel userTopic = new UserTopicLevel(userId, topicId);
		userTopic = this.topicRedis.getUserTopicLevel(userId, topicId);
		return userTopic;
	}
	
	public UserTopicLevel updateUserTopicLevel(String userId, String topicId, int addXP) {
		UserTopicLevel topicLevel = this.getUserTopic(userId, topicId);
		if(topicLevel == null)
			topicLevel = new UserTopicLevel(userId, topicId);
		topicLevel = TopicLevelCompute.updateXP(topicLevel, addXP);
		if(topicLevel == null) {
			topicLevel = new UserTopicLevel("error", "error", 0);
			return topicLevel;
		}
		this.updateUserTopicLevel(topicLevel);
		return topicLevel;
	}
	

	
	public void removeUserTopicLevel(String userId, String topicId) {
		this.topicRedis.removeUserTopicLevel(userId, topicId);
	}
	
	public void removeAllUserTopicLevel(String userId) {
		List<UserTopicLevel> topicList = new ArrayList<UserTopicLevel>();
		topicList = this.topicRedis.getUserLevelByUserId(userId);
		
		for(UserTopicLevel topicLevel : topicList) {
			this.removeUserTopicLevel(userId, topicLevel.getTopicId());
		}
	}
	
	public boolean updateUserTopicLevel(UserTopicLevel topicLevel) {
		this.topicRedis.updateUserTopicLevel(topicLevel);
		return true;
	}

}
