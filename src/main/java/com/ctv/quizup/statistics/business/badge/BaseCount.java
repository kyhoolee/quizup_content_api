package com.ctv.quizup.statistics.business.badge;

import java.util.List;

import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.redis.BadgeRedis;
import com.ctv.quizup.user.redis.UserBadgeAchievRedis;

public abstract class BaseCount {
	private BadgeBusiness badgeBusiness;
	private UserBadgeAchievRedis userBadgeRedis;
	private BadgeRedis badgeRedis;
	
	public BaseCount(BadgeBusiness business) {
		this.setBadgeBusiness(business);
		this.setUserBadgeRedis(new UserBadgeAchievRedis());
		this.setBadgeRedis(new BadgeRedis());
	}
	
	
	public void countBadge(String userId, CountType type) {
		//List<Badge> badgeList = this.getBadgeRedis().getBadgeListByType(CountType.Match);
		List<BadgeCountInfo> badgeCountList = this.getBadgeRedis().getBadgeCountListByType(type);
		
		for(BadgeCountInfo badgeCount : badgeCountList) {
			String badgeId = badgeCount.getBadgeId();
			int badgeScore = badgeCount.getCountScore();
			Badge badge = this.getBadgeRedis().getBadgeById(badgeId);
			
			
			UserBadgeAchiev badgeAchiev = this.getUserBadgeRedis().getUserAchievement(userId, badgeId);
			if(badgeAchiev != null) {
				badgeAchiev.setCount(badgeAchiev.getCount() + 1);
				badgeAchiev.setProgress(badgeAchiev.getCount() * 1.0 / badgeScore);
				badgeAchiev.setFinished((badgeAchiev.getCount() >= badgeScore));
				
				this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
			} else {
				badgeAchiev = new UserBadgeAchiev(userId, badgeId, badge);
				badgeAchiev.setCount(1);
				badgeAchiev.setProgress(1.0/badgeScore);
				badgeAchiev.setFinished((1 >= badgeScore));
				
				this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
			}
		}
		
	}
	
	public void countConditionBadge(String userId, int value, CountType type) {
		//List<Badge> badgeList = this.getBadgeRedis().getBadgeListByType(CountType.Match);
		List<BadgeCountInfo> badgeCountList = this.getBadgeRedis().getBadgeCountListByType(type);
		
		for(BadgeCountInfo badgeCount : badgeCountList) {
			String badgeId = badgeCount.getBadgeId();
			int badgeScore = badgeCount.getCountScore();
			int condition = badgeCount.getCondition();
			
			if(value >= condition) {
				
				Badge badge = this.getBadgeRedis().getBadgeById(badgeId);
				
				UserBadgeAchiev badgeAchiev = this.getUserBadgeRedis().getUserAchievement(userId, badgeId);
				if(badgeAchiev != null) {
					badgeAchiev.setCount(badgeAchiev.getCount() + 1);
					badgeAchiev.setProgress(badgeAchiev.getCount() * 1.0 / badgeScore);
					badgeAchiev.setFinished((badgeAchiev.getCount() >= badgeScore));
					
					this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
				} else {
					badgeAchiev = new UserBadgeAchiev(userId, badgeId, badge);
					badgeAchiev.setCount(1);
					badgeAchiev.setProgress(1.0/badgeScore);
					badgeAchiev.setFinished((1 >= badgeScore));
					
					this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
				}
			}
		}
		
	}

	/**
	 * @return the badgeBusiness
	 */
	public BadgeBusiness getBadgeBusiness() {
		return badgeBusiness;
	}

	/**
	 * @param badgeBusiness the badgeBusiness to set
	 */
	public void setBadgeBusiness(BadgeBusiness badgeBusiness) {
		this.badgeBusiness = badgeBusiness;
	}
	

	public abstract void count();
	
	public abstract List<UserBadgeAchiev> countBadge();

	/**
	 * @return the userBadgeRedis
	 */
	public UserBadgeAchievRedis getUserBadgeRedis() {
		return userBadgeRedis;
	}

	/**
	 * @param userBadgeRedis the userBadgeRedis to set
	 */
	public void setUserBadgeRedis(UserBadgeAchievRedis userBadgeRedis) {
		this.userBadgeRedis = userBadgeRedis;
	}

	/**
	 * @return the badgeRedis
	 */
	public BadgeRedis getBadgeRedis() {
		return badgeRedis;
	}

	/**
	 * @param badgeRedis the badgeRedis to set
	 */
	public void setBadgeRedis(BadgeRedis badgeRedis) {
		this.badgeRedis = badgeRedis;
	}
}
