package com.ctv.quizup.content.sql.raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.content.business.QuestionImporter;
import com.ctv.quizup.content.business.TopicImporter;
import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;

public class ContentProcess {
	
	public static HashMap<String, RawTopic> getTopicHash(List<RawTopic> topicList) {
		HashMap<String, RawTopic> topicHash = new HashMap<String, RawTopic>();
		for(RawTopic topic : topicList) {
			topicHash.put(topic.title, topic);
		}
		
		return topicHash;
	}
	
	public static HashMap<String, String> getTopicTitleIdHash(HashMap<String, RawTopic> topicHash) {
		HashMap<String, String> titleId = new HashMap<String, String>();
		for(String title : topicHash.keySet()) {
			titleId.put(title, topicHash.get(title).topicId);
		}
		
		return titleId;
	}
	
	public static HashMap<String, List<RawQuestion>> getTopicQuesHash(List<RawQuestion> quesList) {
		HashMap<String, List<RawQuestion>> topicHash = new HashMap<String, List<RawQuestion>>();
		for(RawQuestion question : quesList) {
			for(int i = 0 ; i < question.catList.size() ; i ++) {
				String cat = question.catList.get(i);
				List<RawQuestion> topicQues = topicHash.get(cat);
				if(topicQues == null)
					topicQues = new ArrayList<RawQuestion>();
				
				topicQues.add(question);
				topicHash.put(cat, topicQues);
			}
		}
		
		return topicHash;
	}
	
	public static HashMap<RawTopic, List<RawQuestion>> getTopicQues(HashMap<String, RawTopic> topicHash, 
			HashMap<String, List<RawQuestion>> topicQuestion) {
		HashMap<RawTopic, List<RawQuestion>> topicIdQuestionId = new HashMap<RawTopic, List<RawQuestion>>();
		
		for(String topicTitle : topicQuestion.keySet()) {
			RawTopic topicId = topicHash.get(topicTitle);
			if(topicId == null) {
				//System.out.println(":" + topicTitle + ":");
				continue;
			}
			
			
			
			List<RawQuestion> rawQues = topicQuestion.get(topicTitle);
			if(topicId.topicId == "167") {
				System.out.println(rawQues.size());
				
			}
			
			topicIdQuestionId.put(topicId, rawQues);
		}
		
		return topicIdQuestionId;
	}
	
	public static void writeTopicToRedis() {
		
	}
	
	
	
	public static void importContent() {
		DataConverter converter = new DataConverter();
		
		RawQuestionProcess quesPro = new RawQuestionProcess();
		RawTopicProcess topicPro = new RawTopicProcess();
		
		// List Topic --> already have parent info
		List<RawTopic> topicList = topicPro.getAllTopic();
		// List Root-Topic
		List<RawTopic> rootList = topicPro.getRootTopic(topicList);
		// Topic-SubTopic Relation
		Map<RawTopic, List<RawTopic>> topicTree = topicPro.getSubTopic(topicList, rootList);
		// TopicTitle-Topic Map
		HashMap<String, RawTopic> topicHash = getTopicHash(topicList);
		
		
		// List Question
		List<RawQuestion> rawQuestion = quesPro.getAllQuestion();
		
		// List Question of each TopicTitle
		HashMap<String, List<RawQuestion>> topicQuestion = getTopicQuesHash(rawQuestion);
		
		HashMap<RawTopic, List<RawQuestion>> rawTopicQuestion = getTopicQues(topicHash, topicQuestion);
		
		
		List<Topic> redisTopicList = converter.transformTopic(topicList);
		
		List<Question> redisQuestionList = converter.transformQues(rawQuestion);
		
		HashMap<Topic, List<Question>> redisTopicQuestion = converter.transformTopicQuestion(rawTopicQuestion);
		
		TopicImporter.writeTopicData(redisTopicList);
		QuestionImporter.writeQuestionData(redisQuestionList);
		QuestionImporter.writeTopicQuestion(redisTopicQuestion);
	}
	
	
	public static void main(String[] args) {
		ContentProcess.importContent();
	}

}
