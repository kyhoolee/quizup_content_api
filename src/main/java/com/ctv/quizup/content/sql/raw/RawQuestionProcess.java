package com.ctv.quizup.content.sql.raw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.content.sql.JDBCProcess;

public class RawQuestionProcess {
	public List<RawQuestion> getListQuestion(ResultSet topicSet) {
		List<RawQuestion> questionList = new ArrayList<RawQuestion>();
		
		try {
			while (topicSet.next()) {
				
				RawQuestion question = new RawQuestion();
				question.id = topicSet.getString("id");
				question.question = topicSet.getString("question");
				question.caseA = topicSet.getString("caseA");
				question.caseB = topicSet.getString("caseB");
				question.caseC = topicSet.getString("caseC");
				question.caseD = topicSet.getString("caseD");
				question.trueCase = topicSet.getString("trueCase");
				question.level = topicSet.getInt("level");
				
				String catList = topicSet.getString("category");
				String[] cat = catList.split(", ");
				question.addCat(cat);
				
				
				questionList.add(question);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return questionList;
	}
	
	public List<RawQuestion> getQuestionFromDB(String sql) {
		List<RawQuestion> questionList = new ArrayList<RawQuestion>();
		
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
	
	public List<RawQuestion> getAllQuestion(int start, int number) {
		List<RawQuestion> questionList = new ArrayList<RawQuestion>();
		
		String sqlStatement = "SELECT * FROM mergequestion LIMIT " + start + ", " + number;
		questionList = this.getQuestionFromDB(sqlStatement);
		
		return questionList;
	}
	
	public List<RawQuestion> getAllQuestion() {
		List<RawQuestion> questionList = new ArrayList<RawQuestion>();
		
		String sqlStatement = "SELECT * FROM mergequestion";
		questionList = this.getQuestionFromDB(sqlStatement);
		
		
		
		return questionList;
	}
	
	public static void main(String[] args) {
		RawQuestionProcess process = new RawQuestionProcess();
		List<RawQuestion> quesList = process.getAllQuestion(0, 30);
		
		for(int i = 0 ; i < quesList.size() ; i ++) {
			//System.out.println(quesList.get(i).toString());
			for(int j = 0 ; j < quesList.get(i).catList.size() ; j ++) {
				System.out.print(quesList.get(i).catList.get(j) + ":---:");
			}
			System.out.println();
		}
	}
}
