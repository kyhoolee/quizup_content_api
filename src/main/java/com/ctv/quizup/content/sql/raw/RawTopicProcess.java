package com.ctv.quizup.content.sql.raw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.content.sql.JDBCProcess;

public class RawTopicProcess {
	public List<RawTopic> getListTopic(ResultSet topicSet) {
		List<RawTopic> topicList = new ArrayList<RawTopic>();
		
		try {
			while (topicSet.next()) {
				
				RawTopic topic = new RawTopic();
				topic.idCode = topicSet.getString("id");
				topic.title = topicSet.getString("nameCate").trim();
				if(topic.title == "")
				System.out.println(":" + topic.title + ":");
				
				
				topicList.add(topic);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return topicList;
	}
	
	public List<RawTopic> getTopicFromDB(String sql) {
		List<RawTopic> questionList = new ArrayList<RawTopic>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			questionList = this.getListTopic(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return questionList;
	}
	
	public List<RawTopic> getAllTopic(int start, int number) {
		List<RawTopic> questionList = new ArrayList<RawTopic>();
		
		String sqlStatement = "SELECT * FROM categoryTree LIMIT " + start + ", " + number;
		questionList = this.getTopicFromDB(sqlStatement);
		
		return questionList;
	}
	
	public List<RawTopic> getAllTopic() {
		List<RawTopic> topicList = new ArrayList<RawTopic>();
		
		String sqlStatement = "SELECT * FROM categoryTree";
		topicList = this.getTopicFromDB(sqlStatement);
		
		for(int i = 0 ; i < topicList.size() ; i ++) {
			topicList.get(i).topicId = "" + i;
		}
		
		return topicList;
	}
	
	public static void main(String[] args) {
		RawTopicProcess process = new RawTopicProcess();
		List<RawTopic> topicList = process.getAllTopic();
		System.out.println(topicList.size());
		
//		for(int i = 0 ; i < topicList.size() ; i ++) {
//			RawTopic topic = topicList.get(i);
//			System.out.println(topic.title + ":----:" + topic.idCode);
//		}
		
		List<RawTopic> rootList = process.getRootTopic(topicList);
		process.printTopic(rootList);
		
		Map<RawTopic, List<RawTopic>> topicTree = process.getSubTopic(topicList, rootList);
		
		for(RawTopic root : topicTree.keySet()) {
			System.out.println("Root: " + root.toString());
			process.printTopic(topicTree.get(root));
			
			System.out.println("------------------------------------------------");
		}
	}
	
	public void printTopic(List<RawTopic> topicList ) {
		for(int i = 0 ; i < topicList.size() ; i ++) {
			System.out.println(topicList.get(i).toString());
		}
	}
	
	public Map<RawTopic, List<RawTopic>> getSubTopic(List<RawTopic> topicList, List<RawTopic> rootList) {
		Map<RawTopic, List<RawTopic>> topicTree = new HashMap<RawTopic, List<RawTopic>>();
		
		for(RawTopic root : rootList) {
			List<RawTopic> subTopic = this.getSubTopic(topicList, root);
			topicTree.put(root, subTopic);
		}
		
		return topicTree;
	}
	
	
	public List<RawTopic> getSubTopic(List<RawTopic> topicList, RawTopic root) {
		List<RawTopic> subList = new ArrayList<RawTopic>();
		
		for(int i = 0 ; i < topicList.size() ; i ++) {
			RawTopic topic = topicList.get(i);
			if(topic.idCode.length() > 1) {
				if(topic.idCode.startsWith(root.idCode)) {
					subList.add(topic);
					topic.parent = root.topicId;
				}
			}
		}
		
		return subList;
	}
	
	public List<RawTopic> getRootTopic(List<RawTopic> topicList) {
		List<RawTopic> rootList = new ArrayList<RawTopic>();
		
		for(int i = 0 ; i < topicList.size() ; i ++) {
			RawTopic topic = topicList.get(i);
			if(topic.idCode.length() == 1) {
				rootList.add(topic);
			}
		}
		
		return rootList;
	}
	
}
