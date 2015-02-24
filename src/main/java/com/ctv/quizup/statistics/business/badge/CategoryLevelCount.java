package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.statistics.business.LevelBusiness;
import com.ctv.quizup.statistics.business.TopicBusiness;
import com.ctv.quizup.user.business.impl.UserTopicProcess;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.model.UserTopicLevel;

public class CategoryLevelCount extends BaseCount {
	
	public static final String MUSIC = "1";
	public static final String ENTERTAINMENT = "9";
	public static final String EDUCATION = "8";
	public static final String LIFESTYLE = "6";
	public static final String SPORT = "13";
	public static final String SCIENCE = "12";
	public static final String NATURE = "11";
	
	LevelBusiness levelProcess;

	public CategoryLevelCount(BadgeBusiness business) {
		super(business);
		this.levelProcess = new LevelBusiness();
	}

	@Override
	public void count() {
		String matchId = this.getBadgeBusiness().getMatchBase().getMatchId();
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();

		
		this.countLevel(firstId, matchId);
		this.countLevel(secondId, matchId);
	}
	
	public List<BadgeAchiev>  countLevelAchiev(String userId, String matchId) {
		int oldLevel = 0;
		int newLevel = 0;
		
		List<UserTopicLevel> levels = this.levelProcess.getLevelByMatch(userId, matchId);
		oldLevel = levels.get(0).getLevel();
		newLevel = levels.get(1).getLevel();
		
		String topicId = this.levelProcess.getMatchBase().getTopicId();
		TopicRedis topicProcess = new TopicRedis();
		Topic topic = topicProcess.getTopicById(topicId);
		String categoryId = topic.getParentId();
		
		return this.countTopicLevelAchiev(userId, categoryId, topicId, oldLevel, newLevel);
	}
	
	public int countLevel(String userId, String matchId) {
		int oldLevel = 0;
		int newLevel = 0;
		
		List<UserTopicLevel> levels = this.levelProcess.getLevelByMatch(userId, matchId);
		oldLevel = levels.get(0).getLevel();
		newLevel = levels.get(1).getLevel();
		
		String topicId = this.levelProcess.getMatchBase().getTopicId();
		TopicRedis topicProcess = new TopicRedis();
		Topic topic = topicProcess.getTopicById(topicId);
		String categoryId = topic.getParentId();
		
		this.countTopicLevel(userId, categoryId, topicId, oldLevel, newLevel);
		
		return newLevel;
	}
	
	public List<BadgeAchiev> countTopicLevelAchiev(String userId, String categoryId, String topicId, int oldLevel, int level) {
		List<BadgeAchiev> result = new ArrayList<BadgeAchiev>();
		
		if(level > oldLevel) {
			if(categoryId.equalsIgnoreCase(EDUCATION)) {
				result.addAll(this.countAchievEducationTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(ENTERTAINMENT)) {
				result.addAll(this.countAchievEntertainmentTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(MUSIC)) {
				result.addAll(this.countAchievMusicTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(NATURE)) {
				result.addAll(this.countAchievNatureTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(SCIENCE)) {
				result.addAll(this.countAchievScienceTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(LIFESTYLE)) {
				result.addAll(this.countAchievLifeStyleTopic(userId, level));
			} else if(categoryId.equalsIgnoreCase(SPORT)) {
				result.addAll(this.countAchievSportTopic(userId, level));
			} 
		}
		return result;
	}
	
	public void countTopicLevel(String userId, String categoryId, String topicId, int oldLevel, int level) {
		if(level > oldLevel) {
			if(categoryId.equalsIgnoreCase(EDUCATION)) {
				this.countEducationTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(ENTERTAINMENT)) {
				this.countEntertainmentTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(MUSIC)) {
				this.countMusicTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(NATURE)) {
				this.countNatureTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(SCIENCE)) {
				this.countScienceTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(LIFESTYLE)) {
				this.countLifeStyleTopic(userId, level);
			} else if(categoryId.equalsIgnoreCase(SPORT)) {
				this.countSportTopic(userId, level);
			} 
		}
	}

	public void checkMusicTopic(String userId) {
		
	}
	
	public void countMusicTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_Music);
	}
	public void countSportTopic(String userId, int level ) {
		this.countConditionBadge(userId, level, CountType.Topic_Sport);
	}
	public void countEducationTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_Education);
	}
	public void countEntertainmentTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_Entertainment);
	}
	public void countNatureTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_Nature);
	}
	public void countLifeStyleTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_LifeStyle);
	}
	public void countScienceTopic(String userId, int level) {
		this.countConditionBadge(userId, level, CountType.Topic_Science);
	}
	
	public List<BadgeAchiev> countAchievMusicTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Music);
	}
	public List<BadgeAchiev> countAchievSportTopic(String userId, int level ) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Sport);
	}
	public List<BadgeAchiev> countAchievEducationTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Education);
	}
	public List<BadgeAchiev> countAchievEntertainmentTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Entertainment);
	}
	public List<BadgeAchiev> countAchievNatureTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Nature);
	}
	public List<BadgeAchiev> countAchievLifeStyleTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_LifeStyle);
	}
	public List<BadgeAchiev> countAchievScienceTopic(String userId, int level) {
		return this.countAchievConditionBadge(userId, level, CountType.Topic_Science);
	}
	
	
	@Override
	public List<BadgeAchiev> countBadge() {
		
		return null;
	}

	@Override
	public Map<String, List<BadgeAchiev>> countUserBadge() {
		Map<String, List<BadgeAchiev>> result = new HashMap<String, List<BadgeAchiev>>();
		
		String matchId = this.getBadgeBusiness().getMatchBase().getMatchId();
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();

		
		result.put(firstId, this.countLevelAchiev(firstId, matchId));
		result.put(secondId, this.countLevelAchiev(secondId, matchId));
		
		return result;
	}

}
