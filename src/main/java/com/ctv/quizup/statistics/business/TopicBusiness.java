package com.ctv.quizup.statistics.business;

import com.ctv.quizup.content.redis.ContentRankType;
import com.ctv.quizup.content.redis.TopicRedis;

public class TopicBusiness extends MatchBaseBusiness {
	TopicRedis topicRedis;
	
	public TopicBusiness() {
		this.topicRedis = new TopicRedis();
	}
	
	public void process(String matchId) {
		this.initProcess(matchId);
		this.updateCount();
	}
	
	public void initProcess(String matchId) {
		super.initProcess(matchId);
		
		
		
	}
	
	public void updateCount() {
		String topicId = super.getMatchBase().getTopicId();
		this.updateTopicPopular(topicId);
		this.updateTopicCrowd(topicId);
		this.updateTopicHot(topicId);
		
	}
	
	public void updateTopicPopular(String topicId) {
		this.topicRedis.updateTopicIncCount(topicId, 1, ContentRankType.TOPIC_POPULAR_RANKING);
	}
	
	public void updateTopicHot(String topicId) {
		
	}
	
	public void updateTopicCrowd(String topicId) {
		
	}

}
