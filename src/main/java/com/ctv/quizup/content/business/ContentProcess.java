package com.ctv.quizup.content.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.ContentRankType;
import com.ctv.quizup.content.redis.QuestionRedis;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.user.business.impl.UserTopicProcess;
import com.ctv.quizup.user.model.UserTopicLevel;

public class ContentProcess {
	QuestionRedis questionRedis;
	TopicRedis topicRedis;
	UserTopicProcess levelProcess;
	


	public ContentProcess() {
		this.questionRedis = new QuestionRedis();
		this.topicRedis = new TopicRedis();
		this.levelProcess = new UserTopicProcess();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PERSONAL-TOPIC-CRUD
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static class UserTopicFullLevel extends Topic {
		private int totalXP = 0;
		private int level = 1;
		private int levelXP = 0;
		private int levelBaseXP = 200;
		
		private int playTimes;

		public UserTopicFullLevel() {
			super();
		}
		
		public UserTopicFullLevel(Topic topic) {
			super(topic);
		}
		
		public UserTopicFullLevel(UserTopicLevel level) {
			super();
			this.setUserLevel(level);
		}
		
		public UserTopicFullLevel(UserTopicLevel level, Topic topic) {
			super(topic);
			this.setUserLevel(level);
		}
		
		public void setUserLevel(UserTopicLevel level) {
			this.totalXP = level.getTotalXP();
			this.level = level.getLevel();
			this.levelXP = level.getLevelXP();
			this.levelBaseXP = level.getLevelBaseXP();
			this.playTimes = level.getPlayTimes();
		}
		
	
		
		public int getTotalXP() {
			return totalXP;
		}

		public void setTotalXP(int totalXP) {
			this.totalXP = totalXP;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getLevelXP() {
			return levelXP;
		}

		public void setLevelXP(int levelXP) {
			this.levelXP = levelXP;
		}

		public int getLevelBaseXP() {
			return levelBaseXP;
		}

		public void setLevelBaseXP(int levelBaseXP) {
			this.levelBaseXP = levelBaseXP;
		}

		public int getPlayTimes() {
			return playTimes;
		}

		public void setPlayTimes(int playTimes) {
			this.playTimes = playTimes;
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
	
	public List<UserTopicFullLevel> getUserTopicFullLevelList(List<Topic> topicList, String userId) {
		List<UserTopicFullLevel> levelList = new ArrayList<UserTopicFullLevel>();
		
		for(Topic topic : topicList) {
			UserTopicLevel topicLevel = this.levelProcess.getUserTopic(userId, topic.getTopicId());
			UserTopicFullLevel level = new UserTopicFullLevel(topicLevel, topic);
			levelList.add(level);
		}

		return levelList;
	}
	

	public List<UserTopicFullLevel> getUserNewTopic( String userId, int start, int size) {
		List<Topic> topicList = topicRedis.getTopicByRankScore(ContentRankType.TOPIC_NEW_RANKING, start, size);
		return this.getUserTopicFullLevelList(topicList, userId);
	}
	

	public List<UserTopicFullLevel> getUserHotTopic(String userId, int start, int size) {
		List<Topic> topicList = topicRedis.getTopicByRankScore(ContentRankType.TOPIC_POPULAR_RANKING, start, size);
		return this.getUserTopicFullLevelList(topicList, userId);
	}
	
	
	public List<UserTopicFullLevel> getUserSubTopicList( String userId, String topicId) {
		List<Topic> topicList = topicRedis.getSubTopic(topicId);
		return this.getUserTopicFullLevelList(topicList, userId);
	}
	
	public List<UserTopicFullLevel> getUserAllSubTopicList( String userId) {
		List<Topic> topicList = this.getAllSubTopicList();
		return this.getUserTopicFullLevelList(topicList, userId);
	}
	
	
	public List<Topic> getAllSubTopicList() {
		List<Topic> topicList = new ArrayList<Topic>();
		
		List<Topic> rootList = this.topicRedis.getAllRootTopic();
		for(Topic root : rootList) {
			List<Topic> subList = this.topicRedis.getSubTopic(root.getTopicId());
			topicList.addAll(subList);
		}
		
		return topicList;
	}
	
	public List<Question> getUserQuestionPackage(
			 String firstId, String secondId, String topicId, int level, int number) {

		return this.questionRedis
				.getRandomQuestionPackage(topicId, level, ContentRankType.QUESTION_TIME_SCORE_TYPE, number);
				//.getRandomQuestionByScore(topicId, level, ContentRankType.QUESTION_TIME_SCORE_TYPE, number);
	}
}
