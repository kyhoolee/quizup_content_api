package com.ctv.quizup.statistics.business;

import java.util.ArrayList;
import java.util.List;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.statistics.business.badge.BaseCount;
import com.ctv.quizup.statistics.business.badge.CompetitorCount;
import com.ctv.quizup.statistics.business.badge.MatchResultCount;
import com.ctv.quizup.statistics.business.badge.QuestionLogCount;
import com.ctv.quizup.statistics.business.badge.TopicCount;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.redis.BadgeRedis;

public class BadgeBusiness extends MatchBaseBusiness {
	MatchResultCount resultCount;
	QuestionLogCount logCount;
	TopicCount topicCount;
	CompetitorCount compCount;
	
	List<BaseCount> baseCountList;
	
	
	
	
	public MatchResultCount getResultCount() {
		return resultCount;
	}

	public void setResultCount(MatchResultCount resultCount) {
		this.resultCount = resultCount;
	}

	public QuestionLogCount getLogCount() {
		return logCount;
	}

	public void setLogCount(QuestionLogCount logCount) {
		this.logCount = logCount;
	}

	public TopicCount getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(TopicCount topicCount) {
		this.topicCount = topicCount;
	}

	public CompetitorCount getCompCount() {
		return compCount;
	}

	public void setCompCount(CompetitorCount compCount) {
		this.compCount = compCount;
	}

	public List<BaseCount> getBaseCountList() {
		return baseCountList;
	}

	public void setBaseCountList(List<BaseCount> baseCountList) {
		this.baseCountList = baseCountList;
	}

	

	public BadgeBusiness() {
	}
	
	public void process(String matchId) {
		this.initProcess(matchId);
		this.updateCount();
	}
	
	public List<UserBadgeAchiev> processUserBadge(String matchId, String userId) {
		List<UserBadgeAchiev> badgeList = new ArrayList<UserBadgeAchiev>(); 
		
		
		return badgeList;
	}
	
	public void initProcess(String matchId) {
		super.initProcess(matchId);
		this.baseCountList = new ArrayList<BaseCount>();
		
		this.resultCount = new MatchResultCount(this);
		this.baseCountList.add(this.resultCount);
		this.logCount = new QuestionLogCount(this);
		this.baseCountList.add(this.logCount);
		this.topicCount = new TopicCount(this);
		this.baseCountList.add(this.topicCount);
		this.compCount = new CompetitorCount(this);
		this.baseCountList.add(this.compCount);
		
		
		String firstId = this.getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getFirstLog().getQuesLog();
		
		String secondId = this.getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getSecondLog().getQuesLog();
		
		String topicId = this.getMatchBase().getTopicId();
		
		int result = this.getMatchResult().getResult();
		
		
		
		
	}
	
	public void updateCount() {
		for(BaseCount base : this.baseCountList) {
			base.count();
		}
	}
	
	public List<UserBadgeAchiev> updateBadgeCount() {
		List<UserBadgeAchiev> badgeList = new ArrayList<UserBadgeAchiev>();
		
		for(BaseCount base : this.baseCountList) {
			List<UserBadgeAchiev> badges = base.countBadge();
			badgeList.addAll(badges);
		}
		
		return badgeList;
	}

	public void updateMatchResultCount() {
		this.resultCount.count();
	}
	
	public void updateQuestionLogCount() {
		this.logCount.count();
	}
	
	public void updateTopicCount() {
		this.topicCount.count();
	}
	
	public void updateCompetitorCount() {
		this.compCount.count();
	}



}
