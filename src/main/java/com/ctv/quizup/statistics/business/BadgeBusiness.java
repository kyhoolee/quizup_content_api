package com.ctv.quizup.statistics.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.statistics.business.badge.BaseCount;
import com.ctv.quizup.statistics.business.badge.CompetitorCount;
import com.ctv.quizup.statistics.business.badge.FrequencyCount;
import com.ctv.quizup.statistics.business.badge.MatchResultCount;
import com.ctv.quizup.statistics.business.badge.QuestionLogCount;
import com.ctv.quizup.statistics.business.badge.CategoryLevelCount;
import com.ctv.quizup.statistics.business.badge.TopicCount;
import com.ctv.quizup.statistics.redis.TriggerStatsRedis;
import com.ctv.quizup.user.model.BadgeAchiev;

public class BadgeBusiness extends MatchBaseBusiness {
	MatchResultCount resultCount;
	QuestionLogCount logCount;
	CategoryLevelCount categoryCount;
	TopicCount topicCount;
	FrequencyCount freqCount;
	CompetitorCount compCount;
	
	List<BaseCount> baseCountList;
	
	
	private TriggerStatsRedis triggerStatRedis;
	

	
	
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

	public CategoryLevelCount getTopicCount() {
		return categoryCount;
	}

	public void setTopicCount(CategoryLevelCount topicCount) {
		this.categoryCount = topicCount;
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
		super();
		this.setTriggerStatRedis(new TriggerStatsRedis());
	}
	
	
	
	public void process(String matchId) {
		this.initProcess(matchId);
		this.updateCount();
	}
	
	public Map<String, List<BadgeAchiev>> processBadge(String matchId) {
		this.initProcess(matchId);
		return this.getUpdateCount();
	}
	
	public List<BadgeAchiev> processUserBadge(String matchId, String userId) {
		List<BadgeAchiev> badgeList = new ArrayList<BadgeAchiev>(); 
		
		
		return badgeList;
	}
	
	public void initProcess(String matchId) {
		super.initProcess(matchId);
		this.baseCountList = new ArrayList<BaseCount>();
		
		this.resultCount = new MatchResultCount(this);
		this.baseCountList.add(this.resultCount);
		
		this.logCount = new QuestionLogCount(this);
		this.baseCountList.add(this.logCount);
		
		this.categoryCount = new CategoryLevelCount(this);
		this.baseCountList.add(this.categoryCount);
		
		this.topicCount = new TopicCount(this);
		this.baseCountList.add(this.topicCount);
		
		this.freqCount = new FrequencyCount(this);
		this.baseCountList.add(this.freqCount);
		
//		
//		this.compCount = new CompetitorCount(this);
//		this.baseCountList.add(this.compCount);
		
		/*
		String firstId = this.getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getFirstLog().getQuesLog();
		
		String secondId = this.getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getSecondLog().getQuesLog();
		
		String topicId = this.getMatchBase().getTopicId();
		
		int result = this.getMatchResult().getResult();
		*/
		
		
		
	}
	
	public Map<String, List<BadgeAchiev>> getUpdateBadge() {
		return this.resultCount.countUserBadge();
	}
	
	public Map<String, List<BadgeAchiev>> getUpdateCount() {
		Map<String, List<BadgeAchiev>> result = new HashMap<String, List<BadgeAchiev>>();
		
		
		for(BaseCount base : this.baseCountList) {
			Map<String, List<BadgeAchiev>> tmp = base.countUserBadge();
			
			if(tmp != null) {
				for(String userId : tmp.keySet()) {
					List<BadgeAchiev> achievs = tmp.get(userId);
					List<BadgeAchiev> achievList = result.get(userId);
					if(achievList == null) {
						achievList = new ArrayList<BadgeAchiev>();
					}
					if(achievs != null) {
						achievList.addAll(achievs);
						result.put(userId, achievList);
					}
				}
			}
		}
		
		return result;
	}
	
	
	public void updateCount() {
		for(BaseCount base : this.baseCountList) {
			base.count();
		}
	}
	
	public List<BadgeAchiev> updateBadgeCount() {
		List<BadgeAchiev> badgeList = new ArrayList<BadgeAchiev>();
		
		for(BaseCount base : this.baseCountList) {
			List<BadgeAchiev> badges = base.countBadge();
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
		this.categoryCount.count();
	}
	
	public void updateCompetitorCount() {
		this.compCount.count();
	}

	/**
	 * @return the triggerStatRedis
	 */
	public TriggerStatsRedis getTriggerStatRedis() {
		return triggerStatRedis;
	}

	/**
	 * @param triggerStatRedis the triggerStatRedis to set
	 */
	public void setTriggerStatRedis(TriggerStatsRedis triggerStatRedis) {
		this.triggerStatRedis = triggerStatRedis;
	}



}
