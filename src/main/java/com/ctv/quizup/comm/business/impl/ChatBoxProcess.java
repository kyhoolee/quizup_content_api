package com.ctv.quizup.comm.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ctv.quizup.comm.model.Message;
import com.ctv.quizup.comm.redis.ChatRedis;
import com.ctv.quizup.user.business.impl.UserBaseProcess;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.redis.UserBaseInfoRedis;

public class ChatBoxProcess {
	ChatRedis messageRedis;
	//UserBaseInfoRedis userBaseRedis;
	//UserBaseProcess userProcess;
	
	public ChatBoxProcess() {
		this.messageRedis = new ChatRedis();
		//this.userBaseRedis = new UserBaseInfoRedis();
		//this.userProcess = new UserBaseProcess();
	}
	
	public void sendMessage(String senderId, String receiverId, String content, long timeStamp) {
		Message message = new Message(senderId, receiverId, content, timeStamp);
		
		this.messageRedis.writeMessageToSortedSet(message, senderId, receiverId);
		this.messageRedis.writeTalkToSortedSet(timeStamp, senderId, receiverId);
	}
	
	public List<String> getTalkIdList(String userId) {
		return this.messageRedis.getTalkIdList(userId);
	}
	
	public List<UserBaseInfo> getTalkList(String userId) {
		UserServiceProcess userProcess = new UserServiceProcess();
		
		List<String> talkList = this.messageRedis.getTalkIdList(userId);
		
		List<UserBaseInfo> userList = new ArrayList<UserBaseInfo>();
		for(String talkId : talkList) {
			UserBaseInfo user = userProcess.getUser(talkId);//this.userBaseRedis.getUserById(talkId);
			if(user != null) {
				userList.add(user);
				
			}
		}
		
		return userList;
	}
	
//	public List<UserBaseInfo> getTalkListByService(String userId) {
//		UserBaseProcess userProcess = new UserBaseProcess();
//		
//		List<String> talkList = this.messageRedis.getTalkIdList(userId);
//		
//		List<UserBaseInfo> userList = new ArrayList<UserBaseInfo>();
//		for(String talkId : talkList) {
//			UserBaseInfo user = userProcess.getUser(talkId);//this.userBaseRedis.getUserById(talkId);
//			if(user != null) {
//				userList.add(user);
//				
//			}
//		}
//		
//		return userList;
//	}
	
	public UserBaseInfo removeTalk(String userId, String talkId) {
		UserBaseInfo userBaseInfo = null;
		
		this.messageRedis.removeUserTalk(userId, talkId);
		
		return null;
	}
	
	public List<Message> getMessageList(String userId, String talkId) {
		List<Message> messList = new ArrayList<Message>();
		
		messList = this.messageRedis.getUserMessage(userId, talkId);
		
		return messList;
	}
	
	public static void main(String[] args) {
		ChatBoxProcess pro = new ChatBoxProcess();
		pro.sendMessage("1", "2", "content", new Date().getTime());
		
		
	}
}
