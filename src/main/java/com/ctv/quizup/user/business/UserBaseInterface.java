package com.ctv.quizup.user.business;

import com.ctv.quizup.user.model.UserBaseInfo;

public interface UserBaseInterface {
	
	public void createUser(UserBaseInfo user);
	
	public UserBaseInfo getUser(String userId);
	

}
