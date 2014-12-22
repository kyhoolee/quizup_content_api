package com.ctv.quizup.content.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.content.model.Topic;

public class QuestionSQL {
	public List<Question> getListQuestion(ResultSet topicSet) {
		List<Question> questionList = new ArrayList<Question>();
		
		try {
			while (topicSet.next()) {
				 
				String questionId = topicSet.getString("QuestionId");
				
				String content = topicSet.getString("Content");
				String type = topicSet.getString("Type");
				
				Timestamp time = topicSet.getTimestamp("CreatedDate");
				Date createdDate = new Date(time.getTime());
				time = topicSet.getTimestamp("ModifiedDate");
				Date modifiedDate = new Date(time.getTime());

				System.out.println("questionId : " + questionId);
				System.out.println("content : " + content);
				
				Question question = new Question();
				question.setQuestionId(questionId);
				question.setContent(content);
				question.setType(type);
				question.setCreatedDate(createdDate);
				question.setModifiedDate(modifiedDate);
				
				questionList.add(question);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return questionList;
	}
	
	public List<Question> getQuestionFromDB(String sql) {
		List<Question> questionList = new ArrayList<Question>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			questionList = this.getListQuestion(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return questionList;
	}
	
	public List<Question> getAllQuestion(String questionId) {
		List<Question> questionList = new ArrayList<Question>();
		
		String sqlStatement = "SELECT * FROM Question WHERE Question.QuestionId = " + questionId;
		questionList = this.getQuestionFromDB(sqlStatement);
		
		return questionList;
	}
	
	
	
	public List<Question> getTopicQuestion(String topicId) {
		List<Question> questionList = new ArrayList<Question>();
		
		String sqlStatement = "SELECT * FROM Question INNER JOIN TopicQuestion ON Question.QuestionId = TopicQuestion.QuestionId" 
					+ " WHERE TopicQuestion.TopicId = " + topicId;
		questionList = this.getQuestionFromDB(sqlStatement);
		
		return questionList;
		
	}
	
	
	
	public static void main(String[] args) {
		//new TopicSQL().getAllRootTopic();
		
	}
}
