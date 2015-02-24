package com.ctv.quizup.user.model;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

public class BadgeAchiev {
	private String userId;
	private String badgeId;
	
	private Badge badge;
	
	private double progress;
	private int count;
	private int max;
	
	private boolean finished;
	
	private Date createdDate;
	private Date modifiedDate;
	
	public BadgeAchiev(String userId, String badgeId) {
		this.userId = userId;
		this.badgeId = badgeId;
		
		this.progress = 0;
		this.setCount(0);
		this.setFinished(false);
		
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}
	
	public BadgeAchiev(String userId, String badgeId, Badge badge) {
		this(userId, badgeId);
		this.badge = badge;
	}
	
	public BadgeAchiev(){
		this.progress = 0;
		this.setFinished(false);
		
		this.createdDate = new Date();
		this.modifiedDate = new Date();
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}
	
	public Badge getBadge() {
		return badge;
	}
	public void setBadge(Badge badge) {
		this.badge = badge;
	}
	
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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


	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}


	/**
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}


}
