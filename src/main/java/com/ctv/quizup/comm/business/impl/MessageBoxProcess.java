package com.ctv.quizup.comm.business.impl;

import java.util.ArrayList;
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
	
	public List<Message> getMessageList(String userId, String talkId, int start, int end) {
		List<Message> messList = new ArrayList<Message>();
		
		messList = this.messageRedis.getUserMessageByTalkId(userId, talkId, start, end);
		
		return messList;
	}
	
	

}
