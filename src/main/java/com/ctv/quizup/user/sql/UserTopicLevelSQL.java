package com.ctv.quizup.user.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.user.model.User;
import com.ctv.quizup.user.model.UserTopicLevel;

public class UserTopicLevelSQL {
	public List<UserTopicLevel> getListUserTopicLevel(ResultSet userSet) {
		List<UserTopicLevel> userList = new ArrayList<UserTopicLevel>();
		
		try {
			while (userSet.next()) {
				 
				String topicId = userSet.getString("TopicId");
				String parentId = userSet.getString("ParentId");
				
				String title = userSet.getString("Title");
				String description = userSet.getString("Description");
				
				Timestamp time = userSet.getTimestamp("CreatedDate");
				Date createdDate = new Date(time.getTime());
				time = userSet.getTimestamp("ModifiedDate");
				Date modifiedDate = new Date(time.getTime());

				System.out.println("userid : " + topicId);
				System.out.println("username : " + parentId);
				
				UserTopicLevel user = new UserTopicLevel(); 
				
				userList.add(user);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return userList;
	}
	
	public List<UserTopicLevel> getUserFromDB(String sql) {
		List<UserTopicLevel> userList = new ArrayList<UserTopicLevel>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			userList = this.getListUserTopicLevel(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return userList;
	}
	
	public List<UserTopicLevel> getAllUser() {
		List<UserTopicLevel> userList = new ArrayList<UserTopicLevel>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = NULL";
		userList = this.getUserFromDB(sqlStatement);
		
		return userList;
	}
	
	
	
	public List<UserTopicLevel> getUserById(String userId) {
		List<UserTopicLevel> userList = new ArrayList<UserTopicLevel>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = " + userId;
		userList = this.getUserFromDB(sqlStatement);
		
		return userList;
		
	}
	
	
	
	public static void main(String[] args) {
		//new TopicSQL().getAllRootTopic();
		
	}
}
