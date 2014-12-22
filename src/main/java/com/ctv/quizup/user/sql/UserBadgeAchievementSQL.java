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

public class UserBadgeAchievementSQL {
	public List<User> getListUser(ResultSet userSet) {
		List<User> userList = new ArrayList<User>();
		
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
				
				User topic = new User();
				
				userList.add(topic);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return userList;
	}
	
	public List<User> getUserFromDB(String sql) {
		List<User> userList = new ArrayList<User>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getDBConnection(); 
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			userList = this.getListUser(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return userList;
	}
	
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = NULL";
		userList = this.getUserFromDB(sqlStatement);
		
		return userList;
	}
	
	
	
	public List<User> getUserById(String userId) {
		List<User> userList = new ArrayList<User>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = " + userId;
		userList = this.getUserFromDB(sqlStatement);
		
		return userList;
		
	}
	
	
	
	public static void main(String[] args) {
		//new TopicSQL().getAllRootTopic();
		
	}
}
