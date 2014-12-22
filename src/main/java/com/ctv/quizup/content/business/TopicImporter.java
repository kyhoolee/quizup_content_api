package com.ctv.quizup.content.business;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.ContentRankType;
import com.ctv.quizup.content.redis.QuestionRedis;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.content.sql.raw.DataConverter.QuestionContent;

public class TopicImporter {
	public static void writeTopicData(List<Topic> topicList) {
		TopicRedis topicRedis = new TopicRedis();
		for(Topic topic : topicList) {
			topicRedis.writeTopicToRedis(topic.toString(), topic.getTopicId());
			if(topic.getParentId() != "") {
				topicRedis.writeSubTopicHashes(topic.getParentId(), topic.getTopicId());
			} else {
				topicRedis.writeRootTopicToRedis(topic.getTopicId());
			}
		}
	}
	
	public static void main(String[] args) {
		TopicRedis topicRedis = new TopicRedis();
		QuestionRedis quesRedis = new QuestionRedis();
		
		List<Topic> topicList = topicRedis.getAllRootTopic();
		for(Topic topic : topicList) {
			System.out.println(topic.toString());
			
			List<Topic> subTopic = topicRedis.getSubTopic(topic.getTopicId());
			for(Topic sub : subTopic) {
				System.out.println(sub.getTopicId() + "--" + sub.getTitle() 
						+ " -- numQues --: " 
						+ quesRedis.getNumQuestionByTopic(sub.getTopicId(), ContentRankType.QUESTION_TIME_SCORE_TYPE));
				List<Question> quesList = quesRedis.getRandomQuestionByScore(sub.getTopicId(), 0, ContentRankType.QUESTION_TIME_SCORE_TYPE, 1);
				
				
				for(Question ques : quesList) {
					//System.out.println(ques.toString());
					//System.out.println(ques.getContent());
					QuestionContent content = null;
					ObjectMapper mapper = new ObjectMapper(); 	
					try {
						content = mapper.readValue(ques.getContent(), QuestionContent.class);
						System.out.println(content.question);
					} catch (Exception e) {
						System.out.println(e.toString());
						
					}
				}
				
				System.out.println("...........");
			}
			
			System.out.println("\n-------------------------------\n");
		}
		
		
	}
}
