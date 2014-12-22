package com.ctv.quizup.statistics.business.badge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.model.UserBadgeAchiev;

public class MatchResultCount extends BaseCount {

	public MatchResultCount(BadgeBusiness business) {
		super(business);
	}

	@Override
	public void count() {
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();
		
		String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		
		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		// COUNT - BADGE
		
		// MATCH-RESULT BADGE
		this.countUserMatchBadge(firstId, result);
		this.countUserMatchBadge(secondId, -result);
		
		// MATCH-COMPLETE BADGE
		if(firstLog.size() == )
		
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
	
	@Override
	public List<UserBadgeAchiev> countBadge() {
		
		return null;
	}
	
}
