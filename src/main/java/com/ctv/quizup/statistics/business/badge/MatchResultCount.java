package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.TopicLevelCompute;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.model.BadgeAchiev;

public class MatchResultCount extends BaseCount {
	ConstantBadgeStore badgeStore;

	public MatchResultCount(BadgeBusiness business) {
		super(business);
		this.badgeStore = new ConstantBadgeStore();
	}

	@Override
	public void count() {
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();
		
		//String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		
		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		
		
		// COUNT - BADGE
		
		// MATCH-RESULT BADGE
		this.countUserMatchBadge(firstId, result);
		this.countUserMatchBadge(secondId, -result);
		
		// MATCH-COMPLETE BADGE
		if(firstLog.size() == TopicLevelCompute.MATCH_LEN) {
			this.countMatch(firstId);
		}
		if(secondLog.size() == TopicLevelCompute.MATCH_LEN) {
			this.countMatch(secondId);
		}
		
	}
	
	public void countUserMatchBadge(String userId) {
		// Count match-badge
		this.countMatch(userId);
	}
	
	
	
	public void countUserMatchBadge(String userId, int result) {
		switch(result) {
		case 1:
			// Count win-match-badge
			this.countWinMatch(userId);
			break;
		case -1:
			// Count lose-match-badge
			this.countLoseMatch(userId);
			break;
		case 0:
			// Count tie-match-badge
			this.countTieMatch(userId);
			break;
		}
		
	}
	
	public List<BadgeAchiev> countUserAchievMatchBadge(String userId, int result) {
		List<BadgeAchiev> achievList = new ArrayList<BadgeAchiev>();
		switch(result) {
		case 1:
			// Count win-match-badge
			achievList.addAll(this.countWinMatchAchiev(userId));
			break;
		case -1:
			// Count lose-match-badge
			achievList.addAll(this.countLoseMatchAchiev(userId));
			break;
		case 0:
			// Count tie-match-badge
			achievList.addAll(this.countTieMatchAchiev(userId));
			break;
		}
		
		return achievList;
		
	}
	
	
	
	public void countMatch(String userId) {
		this.countBadge(userId, CountType.Match);
	}
	public void countWinMatch(String userId) {
		this.countBadge(userId, CountType.Match_Win);
	}
	public void countLoseMatch(String userId) {
		this.countBadge(userId, CountType.Match_Lose);
	}
	public void countTieMatch(String userId) {
		this.countBadge(userId, CountType.Match_Tie);
	}
	
	public List<BadgeAchiev> countMatchAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match);
	}
	public List<BadgeAchiev> countWinMatchAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Win);
	}
	public List<BadgeAchiev> countLoseMatchAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Lose);
	}
	public List<BadgeAchiev> countTieMatchAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Tie);
	}
	

	
//	public List<UserBadgeAchiev> countAchievBadge(String userId, CountType type) {
//		List<UserBadgeAchiev> result = new ArrayList<UserBadgeAchiev>();
//		
//		
//		List<BadgeCountInfo> badgeCountList = this.getBadgeRedis().getBadgeCountListByType(type);
//		//this.badgeStore.getBadgeCountListByType(type);
//		
//		for(BadgeCountInfo badgeCount : badgeCountList) {
//			String badgeId = badgeCount.getBadgeId();
//			int badgeScore = badgeCount.getCountScore();
//			Badge badge = this.getBadgeRedis().getBadgeById(badgeId);
//			//this.badgeStore.getBadgeById(badgeId);
//			
//			
//			UserBadgeAchiev badgeAchiev = this.getUserBadgeRedis().getUserAchievement(userId, badgeId);
//			if(badgeAchiev != null) {
//				badgeAchiev.setCount(badgeAchiev.getCount() + 1);
//				badgeAchiev.setProgress(badgeAchiev.getCount() * 1.0 / badgeScore);
//				badgeAchiev.setMax(badgeScore);
//				badgeAchiev.setFinished((badgeAchiev.getCount() >= badgeScore));
//				
//				this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
//			} else {
//				badgeAchiev = new UserBadgeAchiev(userId, badgeId, badge);
//				badgeAchiev.setCount(1);
//				badgeAchiev.setProgress(1.0/badgeScore);
//				badgeAchiev.setFinished((1 >= badgeScore));
//				badgeAchiev.setMax(badgeScore);
//				this.getUserBadgeRedis().updateUserBadge(badgeAchiev);
//			}
//			
//			if(badgeAchiev.isFinished()) {
//				result.add(badgeAchiev);
//			}
//		}
//		
//		
//		return result;
//	}
	
	@Override
	public List<BadgeAchiev> countBadge() {
		List<BadgeAchiev> achievList = new ArrayList<BadgeAchiev>();
		
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();
		
		//String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		
		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		
		
		// COUNT - BADGE
		
		// MATCH-RESULT BADGE
		achievList.addAll(this.countUserAchievMatchBadge(firstId, result));
		achievList.addAll(this.countUserAchievMatchBadge(secondId, -result));
		
		// MATCH-COMPLETE BADGE
		if(firstLog.size() == TopicLevelCompute.MATCH_LEN) {
			achievList.addAll(this.countMatchAchiev(firstId));
		}
		if(secondLog.size() == TopicLevelCompute.MATCH_LEN) {
			achievList.addAll(this.countMatchAchiev(secondId));
		}
		
		return achievList;
	}

	@Override
	public Map<String, List<BadgeAchiev>> countUserBadge() {
		
		Map<String, List<BadgeAchiev>> achievMap = new HashMap<String, List<BadgeAchiev>>();
		
		List<BadgeAchiev> firstAchiev = new ArrayList<BadgeAchiev>();
		List<BadgeAchiev> secondAchiev = new ArrayList<BadgeAchiev>();
		
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();
		
		//String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		
		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		
		
		// COUNT - BADGE
		
		
		if(!this.getBadgeBusiness().getTriggerStatRedis().checkUserBadgeMatch(this.getBadgeBusiness().getMatchBase().getMatchId(), firstId)) {
			// MATCH-RESULT BADGE
			firstAchiev.addAll(this.countUserAchievMatchBadge(firstId, result));
			
			// MATCH-COMPLETE BADGE
			if(firstLog.size() == TopicLevelCompute.MATCH_LEN) {
				firstAchiev.addAll(this.countMatchAchiev(firstId));
			}
			
			this.getBadgeBusiness().getTriggerStatRedis().writeUserBadgeMatch(this.getBadgeBusiness().getMatchBase().getMatchId(), firstId);
		}
		if(!this.getBadgeBusiness().getTriggerStatRedis().checkUserBadgeMatch(this.getBadgeBusiness().getMatchBase().getMatchId(), secondId)) {
			secondAchiev.addAll(this.countUserAchievMatchBadge(secondId, -result));
			
			if(secondLog.size() == TopicLevelCompute.MATCH_LEN) {
				secondAchiev.addAll(this.countMatchAchiev(secondId));
			}
			
			this.getBadgeBusiness().getTriggerStatRedis().writeUserBadgeMatch(this.getBadgeBusiness().getMatchBase().getMatchId(), secondId);
		}
		
		
		
		
		achievMap.put(firstId, firstAchiev);
		achievMap.put(secondId, secondAchiev);
		
		
		
		return achievMap;
	}
	
}
