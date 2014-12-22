package com.ctv.quizup.content.sql.raw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.content.business.QuestionType;
import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;

public class DataConverter {
	public static class QuestionContent {
		public String question;
		public String caseA;
		public String caseB;
		public String caseC;
		public String caseD;
		
		public String trueCase;
		
		public QuestionContent() {
			this.question = "";
			this.caseA = "";
			this.caseB = "";
			this.caseC = "";
			this.caseD = "";
			
			this.trueCase = "";
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
	
	public QuestionContent getQuesContent(RawQuestion rawQues) {
		QuestionContent content = new QuestionContent();
		
		content.caseA = rawQues.caseA;
		content.caseB = rawQues.caseB;
		content.caseC = rawQues.caseC;
		content.caseD = rawQues.caseD;
		
		content.question = rawQues.question;
		content.trueCase = rawQues.trueCase;
		
		return content;
	}
	
	public Question transform(RawQuestion rawQues) {
		Question question = new Question();
		
		QuestionContent content = this.getQuesContent(rawQues);
		question.setContent(content.toString());
		
		question.setType(QuestionType.ALTP_QUESTION_TYPE);
		question.setLevel(rawQues.level);
		question.setQuestionId(rawQues.id);
		question.setCreatedDate(new Date());
		question.setModifiedDate(new Date());
		
		return question;
		
	}
	
	public List<Question> transformQues(List<RawQuestion> rawQues) {
		List<Question> quesList = new ArrayList<Question>();
		
		for(RawQuestion ques : rawQues) {
			quesList.add(this.transform(ques));
		}
		
		return quesList;
	}
	
	public Topic transform(RawTopic rawTopic) {
		Topic topic = new Topic();
		
		topic.setCreatedDate(new Date());
		topic.setModifiedDate(new Date());
		
		topic.setDescription(rawTopic.title);
		topic.setTitle(rawTopic.title);
		
		topic.setTopicId(rawTopic.topicId);
		topic.setParentId(rawTopic.parent);
		
		return topic;
	}
	
	public List<Topic> transformTopic(List<RawTopic> rawTopics) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		for(RawTopic rawTopic : rawTopics) {
			topicList.add(this.transform(rawTopic));
		}
		
		return topicList;
	}

	
	public HashMap<Topic, List<Question>> transformTopicQuestion(HashMap<RawTopic, List<RawQuestion>> topicQuestion) {
		HashMap<Topic, List<Question>> topQues = new HashMap<Topic, List<Question>>();
		
		for(RawTopic rawTop : topicQuestion.keySet()) {
			Topic topic = this.transform(rawTop);
			List<Question> quesList = this.transformQues(topicQuestion.get(rawTop));
			
			topQues.put(topic, quesList);
		}
		return topQues;
		
	}
}
