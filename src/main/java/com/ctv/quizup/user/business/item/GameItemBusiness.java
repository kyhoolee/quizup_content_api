package com.ctv.quizup.user.business.item;

import com.ctv.quizup.user.model.GameItem;
import com.ctv.quizup.user.redis.GameItemRedis;

public class GameItemBusiness {
	private GameItemRedis itemRedis;
	
	public GameItemBusiness() {
		this.itemRedis = new GameItemRedis();
	}
	
	public GameItem getItemValue(String userId, String item) {
		long value = this.itemRedis.getUserItem(userId, item);
		GameItem i = new GameItem(userId, item, value);
		
		return i;
	}
	
	public void setItemValue(String userId, String item, long value) {
		this.itemRedis.writeUserItem(userId, item, value);
	}
	
	public void addItemValue(String userId, String item, long add) {
		this.itemRedis.addUserItem(userId, item, add);
	}
	
	public boolean costItemValue(String userId, String item, long cost) {
		return this.itemRedis.checkCostUserItem(userId, item, cost);
	}

}
