package com.ctv.quizup.content.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.ContentRankType;
import com.ctv.quizup.content.redis.QuestionRedis;

public class QuestionImporter {
	
	public static void writeQuestionData(List<Question> questList) {
		QuestionRedis quesRedis = new QuestionRedis();
		for(Question question : questList) {
			quesRedis.writeQuestionToRedis(question.toString(), question.getQuestionId());
		}
	}
	
	public static void writeTopicQuestion(Map<Topic, List<Question>> topicQuestList) {
		for(Topic topic : topicQuestList.keySet()) {
			System.out.println(topic.getTitle());
			List<Question> questList = topicQuestList.get(topic);
			QuestionImporter.writeTopicQuestionData(questList, topic.getTopicId());
		}
		
	}
	
	public static void writeTopicQuestionData(Map<String, List<Question>> topicQuestList) {
		for(String topicId : topicQuestList.keySet()) {
			List<Question> questList = topicQuestList.get(topicId);
			QuestionImporter.writeTopicQuestionData(questList, topicId);
		}
		
	}
	
	public static void writeTopicQuestionData(List<Question> questList, String topicId) {
		QuestionRedis quesRedis = new QuestionRedis();
		for(Question question : questList) {
			//quesRedis.writeQuestionToRedis(question.toString(), question.getQuestionId());
			
			quesRedis.updateQuestionScore(question.getQuestionId(), topicId, question.getLevel(), 
					new Date().getTime(), ContentRankType.QUESTION_TIME_SCORE_TYPE);
			
			// level 0 is the all level
			quesRedis.updateQuestionScore(question.getQuestionId(), topicId, 0, 
					new Date().getTime(), ContentRankType.QUESTION_TIME_SCORE_TYPE);
		}
		
	}
	
	public static List<Question> genQuestionPackage(String topicId, int number, int level) {
		QuestionRedis quesRedis = new QuestionRedis();
		
		List<Question> quesList = quesRedis.getRandomQuestionByScore(topicId, level, ContentRankType.QUESTION_TIME_SCORE_TYPE, number);
		
		return quesList;
	}

	public static void main(String[] args) {
		QuestionRedis quesRedis = new QuestionRedis();
		
		Question ques = quesRedis.getQuestionById("1584");
		System.out.println(ques.toString());
	}
}
