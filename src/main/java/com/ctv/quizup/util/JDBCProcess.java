package com.ctv.quizup.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;
import com.mysql.jdbc.Driver;

public class JDBCProcess {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	static final String DB_URL = "jdbc:mysql://localhost:3306";
	
	static final String USER_DB_URL = "jdbc:mysql://localhost:3306/QuizupUser";
	static final String MATCH_DB_URL = "jdbc:mysql://localhost:3306/QuizupMatch";
	
	static final String CONTENT_DB_URL = "jdbc:mysql://localhost:3306/QuestionContent";

	static final String USER_DB = "QuizupUser";
	static final String MATCH_DB = "QuizupMatch";
	
	public static final String QUIZUP_MATCH_TABLE = "QuizupMatchTable";

	static final String USER = "root";
	static final String PASS = "1";
	
	

	
	
	
//	// ////////////////////////////////////////////////////////////////////////////////////////
//	// CONNECTION POOL
//	// ////////////////////////////////////////////////////////////////////////////////////////
//
//	
//	static ConnectionPool pool;
//
//	static {
//		Properties properties = new Properties();
//		try {
//			properties.load(JDBCProcess.class
//					.getResourceAsStream("/database.properties"));
//		} catch (Exception e) {
//			Logger.getLogger("Api").error("Can not load database.properties");
//		}
//		try {
//			Class<?> c = Class.forName(properties.getProperty("driver",
//					"com.mysql.jdbc.Driver"));
//			Driver driver = (Driver) c.newInstance();
//			DriverManager.registerDriver(driver);
//			
//			pool = new ConnectionPool("db-pool", 20, 50, 3600,
//					properties.getProperty("url", MATCH_DB_URL),
//					properties.getProperty("username", USER),
//					properties.getProperty("password", PASS));
//		} catch (ClassNotFoundException e) {
//			Logger.getLogger("Api").error(
//					"Can not find driver class "
//							+ properties.getProperty("driver"));
//		} catch (InstantiationException e) {
//			Logger.getLogger("Api").error("Can not create driver instance");
//		} catch (IllegalAccessException e) {
//			Logger.getLogger("Api").error("Can not access driver instance");
//		} catch (SQLException e) {
//			Logger.getLogger("Api").error(
//					"Can not register driver due to " + e.getMessage());
//		}
//	}
//
//	public static ConnectionPool getPool() {
//		return pool;
//	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// CONNECT DATABASE
	// ////////////////////////////////////////////////////////////////////////////////////////

	public static Connection getDBConnection(String dbURL) {
		Connection dbConnection = null;
		
		Properties properties = new Properties();
		try {
			properties.load(JDBCProcess.class
					.getResourceAsStream("/database.properties"));
		} catch (Exception e) {
			Logger.getLogger("Api").error("Can not load database.properties");
		}
		
		//String url = properties.getProperty("url", MATCH_DB_URL);
		String user = properties.getProperty("username", USER);
		String pass = properties.getProperty("password", PASS);

		try {

			Class.forName(JDBC_DRIVER);

		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(dbURL, user, pass);

			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;
	}

	public static Connection getContentDB() {
		return getDBConnection(CONTENT_DB_URL);
	}

	public static Connection getUserDB() {
		return getDBConnection(USER_DB_URL);
	}

	public static Connection getMatchDB() {
		Properties properties = new Properties();
		try {
			properties.load(JDBCProcess.class
					.getResourceAsStream("/database.properties"));
		} catch (Exception e) {
			Logger.getLogger("Api").error("Can not load database.properties");
		}
		String matchDBURL = properties.getProperty("url", MATCH_DB_URL);
		return getDBConnection(matchDBURL);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// CREATE DATABASE
	// ////////////////////////////////////////////////////////////////////////////////////////

	public static void createDB(String dbName) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();

			String sql = "CREATE DATABASE " + dbName;
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}
	}

	public static void createUserDB() {
		createDB(USER_DB);
	}

	public static void createMatchDB() {
		createDB(MATCH_DB);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////
	// CREATE DATABASE-TABLE
	// ////////////////////////////////////////////////////////////////////////////////////////
	
	public static void createMatchTable(String dbURL) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(dbURL, USER, PASS);
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			String sql = "CREATE TABLE QuizupMatchTable "
					+ "(id INTEGER not NULL, " 
					
					+ " matchId VARCHAR(16), "
					+ " firstId VARCHAR(16), "
					+ " secondId VARCHAR(16), "
					+ " topicId VARCHAR(16), " 
					
					+ " matchResult INTEGER, "
					+ " firstLog VARCHAR(4000),"
					+ " secondLog VARCHAR(4000),"
					
					+ " createdDate DATE,"
					
					+ " PRIMARY KEY ( id )," 
					+ " UNIQUE INDEX MATCH_INDEX (MatchId),"
					+ " INDEX FIRST_INDEX (firstId),"
					+ " INDEX SECOND_INDEX (secondId),"
					+ " INDEX TOPIC_INDEX (topicId),"
					+ " INDEX TIME_INDEX USING BTREE (createdDate)"
					
					+ ")"
					;

			stmt.executeUpdate(sql);
			
			
			System.out.println("Created table in given database...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}// end main
	
}
