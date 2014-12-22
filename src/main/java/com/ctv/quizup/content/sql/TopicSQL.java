package com.ctv.quizup.content.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.content.model.Topic;

public class TopicSQL {
	
	public List<Topic> getListTopic(ResultSet topicSet) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		try {
			while (topicSet.next()) {
				 
				String topicId = topicSet.getString("TopicId");
				String parentId = topicSet.getString("ParentId");
				
				String title = topicSet.getString("Title");
				String description = topicSet.getString("Description");
				
				Timestamp time = topicSet.getTimestamp("CreatedDate");
				Date createdDate = new Date(time.getTime());
				time = topicSet.getTimestamp("ModifiedDate");
				Date modifiedDate = new Date(time.getTime());

				System.out.println("userid : " + topicId);
				System.out.println("username : " + parentId);
				
				Topic topic = new Topic();
				topic.setTopicId(topicId);
				topic.setParentId(parentId);
				topic.setTitle(title);
				topic.setDescription(description);
				topic.setCreatedDate(createdDate);
				topic.setModifiedDate(modifiedDate);
				
				topicList.add(topic);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return topicList;
	}
	
	public List<Topic> getTopicFromDB(String sql) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			topicList = this.getListTopic(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return topicList;
	}
	
	public List<Topic> getAllRootTopic() {
		List<Topic> topicList = new ArrayList<Topic>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = NULL";
		topicList = this.getTopicFromDB(sqlStatement);
		
		return topicList;
	}
	
	
	
	public List<Topic> getSubTopic(String parentId) {
		List<Topic> topicList = new ArrayList<Topic>();
		
		String sqlStatement = "SELECT * FROM Topic WHERE Topic.ParentId = " + parentId;
		topicList = this.getTopicFromDB(sqlStatement);
		
		return topicList;
		
	}
	
	
	
	public static void main(String[] args) {
		//new TopicSQL().getAllRootTopic();
		
	}
	
}
