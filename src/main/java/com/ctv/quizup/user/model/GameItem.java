package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class GameItem {
	private String userId;
	private String item;
	private long value;
	
	
	public GameItem() {
		super();
	}
	public GameItem(String userId, String item, long value) {
		super();
		this.userId = userId;
		this.item = item;
		this.value = value;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
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
