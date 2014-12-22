package com.ctv.quizup.user.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.model.UserRelation;
import com.ctv.quizup.user.redis.UserBaseInfoRedis;
import com.ctv.quizup.user.redis.UserRelationRedis;

public class UserFriendProcess {
	UserRelationRedis relationRedis;
	
	public UserFriendProcess() {
		this.relationRedis = new UserRelationRedis();
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBaseInfo> getFriendBaseInfo(String userId) {
		List<UserBaseInfo> friendInfo = new ArrayList<UserBaseInfo>();
		
		Set<String> friendList = this.relationRedis.getRelationTarget(userId, UserRelationRedis.RelationType.FRIEND_RELATION);
		
		UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		for(String relation : friendList) {
			UserBaseInfo friendBase = userRedis.getUserById(relation);
			if(friendBase != null) {
				friendInfo.add(friendBase);
			}
		}
		
		return friendInfo;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBaseInfo> getFriendByService(String userId) {
		List<UserBaseInfo> friendInfo = new ArrayList<UserBaseInfo>();
		
		Set<String> friendList = this.relationRedis.getRelationTarget(userId, UserRelationRedis.RelationType.FRIEND_RELATION);
		
		//UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		UserServiceProcess service = new UserServiceProcess();
		for(String relation : friendList) {
			UserBaseInfo friendBase = service.getUser(relation);
			if(friendBase != null) {
				friendInfo.add(friendBase);
			}
		}
		
		return friendInfo;
	}
	
	/**
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public boolean requestFriend(String userId, String friendId) {
		UserRelation relation = UserRelation.createFriend(userId, friendId);
		this.relationRedis.updateUserRelation(relation.toString(), userId, friendId, 
				UserRelationRedis.RelationType.FRIEND_RELATION);
		
		return true;
	}
	
	/**
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public boolean removeFriend(String userId, String friendId) {
		this.relationRedis.removeUserRelation(userId, friendId, 
				UserRelationRedis.RelationType.FRIEND_RELATION);
		
		return true;
	}
	
	
	
}
