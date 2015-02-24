package com.ctv.quizup.user.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.redis.CurrentBoardRedis;
import com.ctv.quizup.user.redis.LeaderBoardRedis;
import com.ctv.quizup.util.LoggerUtil;

public class LeaderBoardProcess {
	public static Logger logger = LoggerUtil.getDailyLogger("LeaderboardBusiness" + "_error");
	public static long PERSONAL_SIZE = 50;
	
	CurrentBoardRedis topicRedis;
	UserRelationProcess friendProcess;
	UserServiceProcess userService;
	
	public LeaderBoardProcess() {
		this.topicRedis = new CurrentBoardRedis();
		this.friendProcess = new UserRelationProcess();
		this.userService = new UserServiceProcess();
	}
	
	public static class TopicRank {
		private String topicId;
		private String userId;
		
		private long rank;
		
		public TopicRank() {
			
		}
		
		public TopicRank(String topicId, String userId, long rank) {
			this.topicId = topicId;
			this.userId = userId;
			this.rank = rank;
		}

		public String getTopicId() {
			return topicId;
		}

		public void setTopicId(String topicId) {
			this.topicId = topicId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public long getRank() {
			return rank;
		}

		public void setRank(long rank) {
			this.rank = rank;
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

	public long getTopicUserRank(String topicId, String userId, String timePeriod) {
		return this.topicRedis.getUserScoreRank(topicId, userId, timePeriod);
	}
	
	public TopicRank getUserTopicRank(String topicId, String userId, String timePeriod) {
		return new TopicRank(topicId, userId, this.getTopicUserRank(topicId, userId, timePeriod));
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-USER-SCORE-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static class TopicUserScore {
		private UserBaseInfo user;
		private String topicId;
		private String scoreType;
		
		private Double score;
		private long rank;

		public TopicUserScore() {
			super();
		}

		public TopicUserScore(UserBaseInfo user, String topicId, String scoreType) {
			super();
			this.user = user;
			this.topicId = topicId;
			this.scoreType = scoreType;
			this.score = 0.0;
			this.rank = -1;
		}

		public TopicUserScore(UserBaseInfo user, String topicId,
				String scoreType, Double score, long rank) {
			super();
			this.user = user;
			this.topicId = topicId;
			this.scoreType = scoreType;
			this.score = score;
			this.rank = rank;
		}


		public UserBaseInfo getUser() {
			return user;
		}


		public void setUser(UserBaseInfo user) {
			this.user = user;
		}


		public String getTopicId() {
			return topicId;
		}


		public void setTopicId(String topicId) {
			this.topicId = topicId;
		}


		public String getScoreType() {
			return scoreType;
		}


		public void setScoreType(String scoreType) {
			this.scoreType = scoreType;
		}


		public Double getScore() {
			return score;
		}


		public void setScore(Double score) {
			this.score = score;
		}


		public long getRank() {
			return rank;
		}


		public void setRank(long rank) {
			this.rank = rank;
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
	
	
	public List<TopicUserScore> getTopicUserList(String topicId, long startIndex, long endIndex, String timePeriod) {
		List<TopicUserScore> userList = new ArrayList<TopicUserScore>();
		
		List<String> userIdList = this.topicRedis.getUserIdListFromTopicRank(topicId, startIndex, endIndex, timePeriod);
		
		for(String userId : userIdList) {
			
			Double score = this.topicRedis.getUserScoreInTopic(topicId, userId, timePeriod);
			long rank = this.topicRedis.getUserScoreRank(topicId, userId, timePeriod);
			UserBaseInfo userBase = this.userService.getUser(userId);
			TopicUserScore topicUser = new TopicUserScore(userBase, topicId, timePeriod, score, rank);
			
			userList.add(topicUser);
		}
		
		return userList;
	}
	
	public List<TopicUserScore> getTopicPersonalList(String topicId, String userId, String timePeriod) {
		
		
		long rank = this.getTopicUserRank(topicId, userId, timePeriod);
		if(rank == -1) {
			return this.getTopicUserList(topicId, 0, 2 * PERSONAL_SIZE, timePeriod);
		}
		if(rank < PERSONAL_SIZE) {
			return this.getTopicUserList(topicId, 0, 2 * PERSONAL_SIZE, timePeriod);
		} else {
			return this.getTopicUserList(topicId, rank - PERSONAL_SIZE, rank + PERSONAL_SIZE, timePeriod);
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TOPIC-USER-FRIEND-SCORE-LIST
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<TopicUserScore> getTopicUserFriendList(String topicId, String userId, int startIndex, int endIndex, String timePeriod) {
		List<TopicUserScore> userList = this.getTopicUserFriendList(topicId, userId, timePeriod);
		
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
		return new ArrayList<TopicUserScore>();
	}
	
	public List<TopicUserScore> getTopicUserFriendList(String topicId, String userId, String timePeriod) {
		List<TopicUserScore> userList = new ArrayList<TopicUserScore>();
		List<UserBaseInfo> friendList = this.friendProcess.getFriendByService(userId);
		
		for(UserBaseInfo friend : friendList) {
			Double score = this.topicRedis.getUserScoreInTopic(topicId, friend.getUserId(), timePeriod);
			long rank = this.topicRedis.getUserScoreRank(topicId, friend.getUserId(), timePeriod);
			
			TopicUserScore topicUser = new TopicUserScore(friend, topicId, timePeriod, score, rank);
			
			userList.add(topicUser);
		}
		
		Double score = this.topicRedis.getUserScoreInTopic(topicId, userId, timePeriod);
		long rank = this.topicRedis.getUserScoreRank(topicId, userId, timePeriod);
		UserBaseInfo userBase = this.userService.getUser(userId);
		TopicUserScore topicUser = new TopicUserScore(userBase, topicId, timePeriod, score, rank);
		
		userList.add(topicUser);
		
		
		Collections.sort(userList, new Comparator<TopicUserScore>() {

			public int compare(TopicUserScore first, TopicUserScore second) {
				Double val1 = first.getScore();
				Double val2 = second.getScore();
				
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
	
	public TopicUserScore getUserTopic(String userId, String topicId, String timePeriod) {
		Double score = this.topicRedis.getUserScoreInTopic(topicId, userId, timePeriod);
		long rank = this.topicRedis.getUserScoreRank(topicId, userId, timePeriod);
		UserBaseInfo userBase = this.userService.getUser(userId);
		TopicUserScore topicUser = new TopicUserScore(userBase, topicId, timePeriod, score, rank);
		return topicUser;
	}
	
	public TopicUserScore getUpdateUserTopicScore(String userId, String topicId, int addXP, String timePeriod) {
		TopicUserScore topicLevel = this.getUserTopic(userId, topicId, timePeriod);
		UserBaseInfo user = this.userService.getUser(userId);
		if(topicLevel == null)
			topicLevel = new TopicUserScore(user, topicId, timePeriod);
		
		this.topicRedis.addUserScoreInTopic(addXP, topicId, userId, timePeriod);
		return topicLevel;
	}
	
	public void updateUserTopicScore(String userId, String topicId, int addXP, String timePeriod) {
		this.topicRedis.addUserScoreInTopic(addXP, topicId, userId, timePeriod);
	}
	
	public void updateUserTopicScore(String userId, String topicId, int addXP) {
		logger.info("userId : " + userId + " -- " + "topicId : " + topicId + " -- " + "addXP : " + addXP);
		
		this.topicRedis.addUserScoreDay(addXP, topicId, userId);
		this.topicRedis.addUserScoreMonth(addXP, topicId, userId);
		this.topicRedis.addUserScoreWeek(addXP, topicId, userId);
	}
	

	
	public void removeUserTopicLevel(String userId, String topicId, String timePeriod) {
		this.topicRedis.removeUserScoreTopic(topicId, userId, timePeriod);
	}
	

}
