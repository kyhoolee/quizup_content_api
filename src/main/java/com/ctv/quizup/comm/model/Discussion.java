package com.ctv.quizup.comm.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class Discussion {
	private String discussionId;
	private String title;
	private String authorId;
	
	private Date createdDate;
	private Date modifiedDate;
	
	
	private List<DiscussionComment> commentList;


	public String getDiscussionId() {
		return discussionId;
	}


	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthorId() {
		return authorId;
	}


	public void setAuthorId(String authorId) {
		this.authorId = authorId;
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


	public List<DiscussionComment> getCommentList() {
		return commentList;
	}


	public void setCommentList(List<DiscussionComment> commentList) {
		this.commentList = commentList;
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
