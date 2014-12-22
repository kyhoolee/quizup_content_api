package com.ctv.quizup.user.model;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class UserBaseInfo {
	private String userId;
	
	private String userName;
	private String avatarURL;
	private String coverURL;
	
	private String locationId;
	private String locAvaURL;
	
	public UserBaseInfo(String userId, String userName, String avatarURL, String coverURL) {
		this.userId = userId;
		this.userName = userName;
		this.avatarURL = avatarURL;
		this.coverURL = coverURL;
	}
	
	public UserBaseInfo() {
		this.userId = "1";
		this.userName = "name";
		this.avatarURL = "avaurl";
		this.coverURL = "coverurl";
		this.locationId = "hanoi";
		this.locAvaURL = "loava";
				
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocAvaURL() {
		return locAvaURL;
	}

	public void setLocAvaURL(String locAvaURL) {
		this.locAvaURL = locAvaURL;
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
