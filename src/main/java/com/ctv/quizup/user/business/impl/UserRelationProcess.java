package com.ctv.quizup.user.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.model.UserRelation;
import com.ctv.quizup.user.redis.UserBaseInfoRedis;
import com.ctv.quizup.user.redis.UserRelationRedis;

public class UserRelationProcess {
	UserRelationRedis relationRedis;
	UserServiceProcess userService;

	public UserRelationProcess() {
		this.relationRedis = new UserRelationRedis();
		this.userService = new UserServiceProcess();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-PEND CRUD
	// ////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBaseInfo> getPendByService(String userId) {
		List<UserBaseInfo> friendInfo = new ArrayList<UserBaseInfo>();

		Set<String> friendList = this.relationRedis.getRelationTarget(userId,
				UserRelationRedis.RelationType.PEND_RELATION);

		// UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		UserServiceProcess service = new UserServiceProcess();
		for (String relation : friendList) {
			UserBaseInfo friendBase = service.getUser(relation);
			if (friendBase != null) {
				friendInfo.add(friendBase);
			}
		}

		return friendInfo;
	}

	/**
	 * 
	 * @param userId
	 * @param targetId
	 * @return
	 */
	public boolean requestPend(String userId, String targetId) {
		targetId = this.userService.checkConvert(targetId);
		
		if(this.checkPend(userId, targetId)) {
			return true;
		}
		
		UserRelation relation = UserRelation.createFriend(userId, targetId);
		this.relationRedis.updateUserRelation(relation.toString(), targetId,
				userId, UserRelationRedis.RelationType.PEND_RELATION);

		return true;
	}
	
	public boolean acceptFriend(String userId, String pendId) {
		if(this.checkPend(userId, pendId)) {
			this.requestFriend(userId, pendId);
			this.requestFriend(pendId, userId);
		}
		
		return true;
	}

	/**
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	public boolean removePend(String userId, String friendId) {
		this.relationRedis.removeUserRelation(userId, friendId,
				UserRelationRedis.RelationType.PEND_RELATION);

		return true;
	}
	
	public boolean checkPend(String userId, String friendId) {
		return this.relationRedis.checkUserRelation(userId, friendId,
				UserRelationRedis.RelationType.PEND_RELATION);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-FRIEND CRUD
	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBaseInfo> getFriendBaseInfo(String userId) {
		List<UserBaseInfo> friendInfo = new ArrayList<UserBaseInfo>();

		Set<String> friendList = this.relationRedis.getRelationTarget(userId,
				UserRelationRedis.RelationType.FRIEND_RELATION);
		UserServiceProcess service = new UserServiceProcess();
		// UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		for (String relation : friendList) {
			UserBaseInfo friendBase = service.getUser(relation);// userRedis.getUserById(relation);
			if (friendBase != null) {
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

		Set<String> friendList = this.relationRedis.getRelationTarget(userId,
				UserRelationRedis.RelationType.FRIEND_RELATION);

		// UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		UserServiceProcess service = new UserServiceProcess();
		for (String relation : friendList) {
			UserBaseInfo friendBase = service.getUser(relation);
			if (friendBase != null) {
				if(friendBase.getUserName() != null) {
					friendInfo.add(friendBase);
				} else {
					this.removeFriend(userId, relation);
				}
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
		friendId = this.userService.checkConvert(friendId);
		if(friendId == null) {
			return false;
		}
		if(this.checkFriend(userId, friendId)) {
			return true;
		}
		
		UserRelation relation = UserRelation.createFriend(userId, friendId);
		this.relationRedis.updateUserRelation(relation.toString(), userId,
				friendId, UserRelationRedis.RelationType.FRIEND_RELATION);

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
	
	public boolean checkFriend(String userId, String friendId) {
		return this.relationRedis.checkUserRelation(userId, friendId,
				UserRelationRedis.RelationType.FRIEND_RELATION);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER-BLOCK CRUD
	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserBaseInfo> getBlockInfo(String userId) {
		List<UserBaseInfo> blockInfo = new ArrayList<UserBaseInfo>();

		Set<String> blockList = this.relationRedis.getRelationTarget(userId,
				UserRelationRedis.RelationType.BLOCK_RELATION);

		// UserBaseInfoRedis userRedis = new UserBaseInfoRedis();
		UserServiceProcess service = new UserServiceProcess();
		for (String relation : blockList) {
			UserBaseInfo blockBase = service.getUser(relation);
			if (blockBase != null) {
				blockInfo.add(blockBase);
			}
		}

		return blockInfo;
	}

	/**
	 * 
	 * @param userId
	 * @param blockId
	 * @return
	 */
	public boolean requestBlock(String userId, String blockId) {
		if(this.checkBlock(userId, blockId)) {
			return true;
		}
		
		UserRelation relation = UserRelation.createBlock(userId, blockId);
		this.relationRedis.updateUserRelation(relation.toString(), userId,
				blockId, UserRelationRedis.RelationType.BLOCK_RELATION);

		return true;
	}

	/**
	 * 
	 * @param userId
	 * @param blockId
	 * @return
	 */
	public boolean removeBlock(String userId, String blockId) {
		this.relationRedis.removeUserRelation(userId, blockId,
				UserRelationRedis.RelationType.BLOCK_RELATION);

		return true;
	}

	public boolean checkBlock(String userId, String blockId) {
		return this.relationRedis.checkUserRelation(userId, blockId,
				UserRelationRedis.RelationType.BLOCK_RELATION);
	}
}
