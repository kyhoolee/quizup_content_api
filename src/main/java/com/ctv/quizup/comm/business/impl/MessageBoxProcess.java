package com.ctv.quizup.comm.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.comm.model.Message;
import com.ctv.quizup.comm.redis.MessageRedis;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.model.UserBaseInfo;

public class MessageBoxProcess {
	MessageRedis messageRedis;
	UserServiceProcess userProcess;
	
	public MessageBoxProcess() {
		this.messageRedis = new MessageRedis();
		this.userProcess = new UserServiceProcess();
	}
	
	public void sendMessage(String senderId, String receiverId, String content, long timeStamp) {
		Message message = new Message(senderId, receiverId, content, timeStamp);
		
		this.messageRedis.writeMessageToRedis(message.toString(), message.getMessageId());
		this.messageRedis.writeMessageToSortedSet(timeStamp, message.getMessageId(), 
				senderId, receiverId);
		this.messageRedis.writeTalkToSortedSet(timeStamp, senderId, receiverId);
	}
	
	public List<UserBaseInfo> getTalkList(String userId, int start, int end) {
		List<String> talkList = this.messageRedis.getTalkIdListByIndex(userId, start, end);
		
		List<UserBaseInfo> userList = new ArrayList<UserBaseInfo>();
		for(String talkId : talkList) {
			UserBaseInfo user = this.userProcess.getUser(talkId);
			if(user != null) {
				userList.add(user);
			}
		}
		
		return userList;
	}
	
	
	public static class DetailTalk extends UserBaseInfo {
		private String message;
		private Date createdDate;
		
		
		public DetailTalk(UserBaseInfo user, String message, Date createdDate) {
			super(user.getUserId(), user.getUserName(), user.getAvatarURL(), user.getCoverURL());
			this.message = message;
			this.createdDate = createdDate;
		}
		
		public DetailTalk(String userId, String userName, String avatarURL,
				String coverURL) {
			super(userId, userName, avatarURL, coverURL);
		}
		public DetailTalk(String userId, String userName, String avatarURL,
				String coverURL, String message, Date createdDate) {
			super(userId, userName, avatarURL, coverURL);
			this.message = message;
			this.createdDate = createdDate;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		
		
	}
	
	public List<DetailTalk> getDetailTalkList(String userId, int start, int end) {
		List<String> talkList = this.messageRedis.getTalkIdListByIndex(userId, start, end);
		List<DetailTalk> talks = new ArrayList<DetailTalk>();
		
		for(String talkId : talkList) {
			UserBaseInfo user = this.userProcess.getUser(talkId);
			List<Message> messList = this.messageRedis.getUserMessageByTalkId(userId, talkId, 0, 0);
			
			if(user != null && messList.size() != 0) {
				Message message = messList.get(0);
				DetailTalk talk = new DetailTalk(user, message.getContent(), message.getCreatedDate());
				
				talks.add(talk);
			}
		}
		
		return talks;
	}
	
	
	public List<Message> getMessageList(String userId, String talkId, int start, int end) {
		List<Message> messList = new ArrayList<Message>();
		
		messList = this.messageRedis.getUserMessageByTalkId(userId, talkId, start, end);
		
		return messList;
	}
	
	

}
