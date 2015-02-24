package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class GameStatus {
	private String status;
	private String userId;
	
	

	public GameStatus() {
		super();
	}

	public GameStatus(String status, String userId) {
		super();
		this.status = status;
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
