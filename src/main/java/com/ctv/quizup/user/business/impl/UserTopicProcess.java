package com.ctv.quizup.user.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.business.ContentProcess.UserTopicFullLevel;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.statistics.TopicLevelCompute;
import com.ctv.quizup.statistics.redis.TriggerStatsRedis;
import com.ctv.quizup.user.business.impl.LeaderBoardProcess.TopicRank;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.user.redis.UserTopicLevelRedis;
import com.ctv.quizup.util.LoggerUtil;



public class UserTopicProcess {
	public static Logger logger = LoggerUtil.getDailyLogger("UserTopicProcess" + "_error"); 
	
	UserTopicLevelRedis topicRedis;
	UserRelationProcess friendProcess;
	UserServiceProcess userService;
	
	public UserTopicProcess() {
		this.topicRedis = new UserTopicLevelRedis();
		this.friendProcess = new UserRelationProcess();
		this.userService = new UserServiceProcess();
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
	
	public List<UserTopicLevel> getUserTopicStats(String userId, int size) {
		List<UserTopicLevel> result = new ArrayList<UserTopicLevel>();
		
		List<UserTopicLevel> topicList = this.getUserTopicList(userId);

		
		UserTopicLevel otherLevel = new UserTopicLevel(userId, "others", 0, new Date());
		otherLevel.setLevel(0);
		
		if(topicList.size() <= size) {
			topicList.add(otherLevel);
			return topicList;
		} else {
			int totalLevel = 0;
			for(int i = size ; i < topicList.size(); i ++) {
				totalLevel += topicList.get(i).getLevel();
			}
			otherLevel.setLevel(totalLevel);
			
			result.addAll(topicList.subList(0, size));
			result.add(otherLevel);
			
			return result;
		}
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
		private long rank;

		public TopicUserFull() {
			super();
		}
		
		public TopicUserFull(UserBaseInfo user) {
			super();
			this.setUser(user);
		}
		
		public TopicUserFull(UserTopicLevel userTopic, UserBaseInfo user, long rank) {
			super(userTopic);
			this.user = user;
			this.rank = rank;
		}
		

		public long getRank() {
			return rank;
		}

		public void setRank(long rank) {
			this.rank = rank;
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
			
			TopicUserFull topicUser = new TopicUserFull(topicLevel, userBase, startIndex);
			startIndex ++;
			
			userList.add(topicUser);
		}
		
		return userList;
	}
	
	public List<TopicUserFull> getTopicPersonalList(String topicId, String userId) {
		long rank = this.topicRedis.getUserLevelRank(topicId, userId);
		
		if(rank == -1) {
			return this.getTopicUserList(topicId, 0, 2 * LeaderBoardProcess.PERSONAL_SIZE);
		}
		if(rank < LeaderBoardProcess.PERSONAL_SIZE) {
			return this.getTopicUserList(topicId, 0, 2 * LeaderBoardProcess.PERSONAL_SIZE);
		} else {
			return this.getTopicUserList(
					topicId, 
					rank - LeaderBoardProcess.PERSONAL_SIZE, 
					rank + LeaderBoardProcess.PERSONAL_SIZE);
		}
	}
	

	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-USER-FRIEND-LEVEL-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<TopicUserFull> getTopicUserFriendList(String topicId, String userId, int startIndex, int endIndex) {
		List<TopicUserFull> userList = this.getTopicUserFriendList(topicId, userId);
		
		if(endIndex == -1) {
			return userList;
		}
		
		if(endIndex > userList.size()) {
			endIndex = userList.size();
		}
		
		try {
			userList = userList.subList(startIndex, endIndex);
			return userList;
		} catch (Exception e) {
			
		}
		return new ArrayList<TopicUserFull>();
	}
	
	public List<TopicUserFull> getTopicUserFriendList(String topicId, String userId) {
		List<TopicUserFull> userList = new ArrayList<TopicUserFull>();
		List<UserBaseInfo> userBaseList = this.friendProcess.getFriendByService(userId);
		
		for(UserBaseInfo friend : userBaseList) {
			UserTopicLevel topicLevel = this.topicRedis.getUserTopicLevel(friend.getUserId(), topicId);
			long rank = this.topicRedis.getUserLevelRank(topicId, friend.getUserId());
			TopicUserFull topicUser = new TopicUserFull(topicLevel, friend, rank);
			userList.add(topicUser);
		}
		
		UserTopicLevel topicLevel = this.topicRedis.getUserTopicLevel(userId, topicId);
		UserBaseInfo user = this.userService.getUser(userId);
		long rank = this.topicRedis.getUserLevelRank(topicId, userId);
		TopicUserFull topicUser = new TopicUserFull(topicLevel, user, rank);
		userList.add(topicUser);
		
		
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
//		userTopic.setUserId(userId);
//		userTopic.setTopicId(topicId);
		return userTopic;
	}
	
	public TopicRank getUserTopicRank(String userId, String topicId) {
		
		//long rank = this.topicRedis.getUserLevelRank(userId, topicId);
		//return rank;
		return new LeaderBoardProcess.TopicRank(topicId, userId, this.topicRedis.getUserLevelRank(topicId, userId));
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
