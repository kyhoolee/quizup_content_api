package com.ctv.quizup.match.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.model.Match;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.util.JDBCProcess;

public class MatchSQL {
	
	
	
	public void createMatchTable() {
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// READ MATCH
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Match> getListMatch(ResultSet matchSet) {
		List<Match> matchList = new ArrayList<Match>();
		
		try {
			while (matchSet.next()) {
				 
				String matchId = matchSet.getString("matchId");
				String firstId = matchSet.getString("firstId");
				String secondId = matchSet.getString("secondId");
				String topicId = matchSet.getString("topicId");
				
				int matchResult = matchSet.getInt("matchResult");
				
				ObjectMapper mapper = new ObjectMapper();
				
				String firstLog = matchSet.getString("firstLog");
				MatchLog first = null;
				try {
					first = mapper.readValue(firstLog, MatchLog.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String secondLog = matchSet.getString("secondLog");
				MatchLog second = null;
				try {
					second = mapper.readValue(secondLog, MatchLog.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Timestamp time = matchSet.getTimestamp("createdDate");
				Date createdDate = new Date(time.getTime());

			
				
				Match match = new Match(
						matchId, firstId, secondId, topicId,
						matchResult, first, second, createdDate
						);
				
				matchList.add(match);

			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		
		return matchList;
	}
	
	public List<Match> getMatchFromDB(String sql) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		String sqlStatement = sql;
		Connection dbConnection;
		Statement statement = null;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			statement = dbConnection.createStatement();
			
			ResultSet result = statement.executeQuery(sqlStatement);
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchFromDB(Date start, Date end) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		Connection dbConnection;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			
			PreparedStatement pstmt = dbConnection.prepareStatement(
					"SELECT * "
		            + " FROM " + JDBCProcess.QUIZUP_MATCH_TABLE
		            + " createdDate BETWEEN ? AND ? "
		            );
			
			pstmt.setDate(1, new java.sql.Date(start.getTime()));
			pstmt.setDate(2, new java.sql.Date(end.getTime()));
			
			ResultSet result = pstmt.executeQuery();
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchById(String matchId, Date start, Date end) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		Connection dbConnection;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			
			PreparedStatement pstmt = dbConnection.prepareStatement(
					"SELECT * "
		            + " FROM " + JDBCProcess.QUIZUP_MATCH_TABLE
		            + " WHERE createdDate BETWEEN ? AND ? "
		            + " AND matchId = '" + matchId + "'"
		            );
			
			pstmt.setDate(1, new java.sql.Date(start.getTime()));
			pstmt.setDate(2, new java.sql.Date(end.getTime()));
			
			ResultSet result = pstmt.executeQuery();
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchByTopicId(String topicId, Date start, Date end) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		Connection dbConnection;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			
			PreparedStatement pstmt = dbConnection.prepareStatement(
					"SELECT * "
		            + " FROM " + JDBCProcess.QUIZUP_MATCH_TABLE
		            + " WHERE createdDate BETWEEN ? AND ? "
		            + " AND topicId = '" + topicId + "'"
		            );
			
			pstmt.setDate(1, new java.sql.Date(start.getTime()));
			pstmt.setDate(2, new java.sql.Date(end.getTime()));
			
			ResultSet result = pstmt.executeQuery();
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchByFirstId(String firstId, Date start, Date end) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		Connection dbConnection;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			
			PreparedStatement pstmt = dbConnection.prepareStatement(
					"SELECT * "
		            + " FROM " + JDBCProcess.QUIZUP_MATCH_TABLE
		            + " WHERE createdDate BETWEEN ? AND ? "
		            + " AND firstId = '" + firstId + "'"
		            );
			
			pstmt.setDate(1, new java.sql.Date(start.getTime()));
			pstmt.setDate(2, new java.sql.Date(end.getTime()));
			
			ResultSet result = pstmt.executeQuery();
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchBySecondId(String secondId, Date start, Date end) {
		
		List<Match> matchList = new ArrayList<Match>();
		
		Connection dbConnection;
		try {
			System.out.println("start connect");
			dbConnection = JDBCProcess.getMatchDB();
			
			PreparedStatement pstmt = dbConnection.prepareStatement(
					"SELECT * "
		            + " FROM " + JDBCProcess.QUIZUP_MATCH_TABLE
		            + " WHERE createdDate BETWEEN ? AND ? "
		            + " AND secondId = '" + secondId + "'"
		            );
			
			pstmt.setDate(1, new java.sql.Date(start.getTime()));
			pstmt.setDate(2, new java.sql.Date(end.getTime()));
			
			ResultSet result = pstmt.executeQuery();
			
			matchList = this.getListMatch(result);
			System.out.println("finish connect");
		} catch (Exception e){
			System.out.println(e.toString());
		}
		return matchList;
	}
	
	public List<Match> getMatchById(String matchId) {
		List<Match> matchList = new ArrayList<Match>();
		
		
		String sqlStatement = 
					"SELECT * FROM " 
					+ JDBCProcess.QUIZUP_MATCH_TABLE
					+ " WHERE matchId = '" + matchId + "'";
		
		matchList = this.getMatchFromDB(sqlStatement);
		
		return matchList;
	}

	public List<Match> getMatchByTopicId(String topicId) {
		List<Match> matchList = new ArrayList<Match>();
		
		
		String sqlStatement = 
					"SELECT * FROM " 
					+ JDBCProcess.QUIZUP_MATCH_TABLE
					+ " WHERE topicId = '" + topicId + "'";
		
		matchList = this.getMatchFromDB(sqlStatement);
		
		return matchList;
	}
	
	public List<Match> getMatchByFirstId(String firstId) {
		List<Match> matchList = new ArrayList<Match>();
		
		
		String sqlStatement = 
					"SELECT * FROM " 
					+ JDBCProcess.QUIZUP_MATCH_TABLE
					+ " WHERE firstId = '" + firstId + "'";
		
		matchList = this.getMatchFromDB(sqlStatement);
		
		return matchList;
	}
	
	public List<Match> getMatchBySecondId(String secondId) {
		List<Match> matchList = new ArrayList<Match>();
		
		
		String sqlStatement = 
					"SELECT * FROM " 
					+ JDBCProcess.QUIZUP_MATCH_TABLE
					+ " WHERE secondId = '" + secondId + "'";
		
		matchList = this.getMatchFromDB(sqlStatement);
		
		return matchList;
	}
	
	public List<Match> getAllMatch() {
		List<Match> matchList = new ArrayList<Match>();
		
		
		String sqlStatement = 
					"SELECT * FROM " 
					+ JDBCProcess.QUIZUP_MATCH_TABLE;
		
		matchList = this.getMatchFromDB(sqlStatement);
		
		return matchList;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// WRITE MATCH
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void insertMatch(Match match) throws SQLException {
		 
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
 
		String insertTableSQL = 
				"INSERT INTO " + JDBCProcess.QUIZUP_MATCH_TABLE
				+ " (matchId, firstId, secondId, topicId, matchResult, firstLog, secondLog, createdDate) VALUES"
				+ " (?,?,?,? ,?,?,? , ?)";
 
		try {
			dbConnection = JDBCProcess.getMatchDB();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
 
			preparedStatement.setString(1, match.getBaseInfo().getMatchId());
			preparedStatement.setString(2, match.getBaseInfo().getFirstUserId());
			preparedStatement.setString(3, match.getBaseInfo().getSecondUserId());
			preparedStatement.setString(4, match.getBaseInfo().getTopicId());
			
			preparedStatement.setInt(5, match.getResult().getResult());
			
			preparedStatement.setString(6, match.getFirstLog().toString());
			preparedStatement.setString(7, match.getSecondLog().toString());
			
			preparedStatement.setTimestamp(8, new Timestamp(match.getBaseInfo().getCreatedDate().getTime()));
 
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
 
			System.out.println("Record is inserted into table!");
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		} finally {
 
			if (preparedStatement != null) {
				preparedStatement.close();
			}
 
			if (dbConnection != null) {
				dbConnection.close();
			}
 
		}
 
	}
	
}
