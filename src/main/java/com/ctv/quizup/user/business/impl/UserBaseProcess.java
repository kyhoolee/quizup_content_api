package com.ctv.quizup.user.business.impl;

import com.ctv.quizup.user.business.UserBaseInterface;
import com.ctv.quizup.user.model.GameStatus;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.redis.UserBaseInfoRedis;

public class UserBaseProcess implements UserBaseInterface {
	UserBaseInfoRedis userRedis;
	
	public UserBaseProcess() {
		this.userRedis = new UserBaseInfoRedis();
	}
	
	public void createUser(UserBaseInfo user) {
		this.userRedis.writeUserToRedis(user.toString(), user.getUserId());
	}
	
	public UserBaseInfo getUser(String userId) {
		return this.userRedis.getUserById(userId);
	}
	
	public void createStatus(GameStatus status) {
		this.userRedis.writeGameStatus(status.toString(), status.getUserId());
	}
	
	public GameStatus getStatus(String userId) {
		return this.userRedis.getGameStatus(userId);
	}
	

}
