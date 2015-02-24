package com.ctv.quizup.statistics.business.badge;

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
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.model.UserTopicLevel;

public class TopicCount extends BaseCount {
/*
2.1:  m nhạc (6 topic)
Thần tượng âm nhạc: level 10 của 2 topic
Siêu sao âm nhạc: Level 10 của 5 topic

2.2.: Giải trí: (6 topic)
Người nổi tiếng: level 10 ở 3 topic
Ông/Nữ hoàng giải trí: level 10 ở 5 topic

2.3: Thể thao: (2 topic)
Vận động viên: Chơi topic thể thao
Chuyên nghiệp: Chơi 2 topic thể thao

2.4: Giáo dục (9 topic)
Hiểu biết: Level 10 của 3 topic
Thông thái: Level 10 của 5 topic
Bậc thầy: Level 10 của 7 topic
Thánh: level 10 của 9 topic

2.5: Tự nhiên (4 topic)
Nhà nghiên cứu: level 10 của 2 topic
Giáo sư : level 10 của 4 topic

2.6: Phong cách sống ( 7 topic)
Sành: level 10 của 3 topic
Lạ: Level 10 của 5 topic
Độc: Level 10 của 7 topic

2.7: Khoa học(4 topic)
Nhà khoa học: level 10 của 2 topic
Người khổng lồ: level 10 của 4 topic

 */
	
	public static final String MUSIC = "1";
	public static final String ENTERTAINMENT = "9";
	public static final String EDUCATION = "8";
	public static final String LIFESTYLE = "6";
	public static final String SPORT = "13";
	public static final String SCIENCE = "12";
	public static final String NATURE = "11";
	
	LevelBusiness levelProcess;

	public TopicCount(BadgeBusiness business) {
		super(business);
		this.levelProcess = new LevelBusiness();
	}

	@Override
	public void count() {
		String matchId = this.getBadgeBusiness().getMatchBase().getMatchId();
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();
		
		String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		
		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		this.countLevel(firstId, matchId);
		this.countLevel(secondId, matchId);
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
	
	
	@Override
	public List<UserBadgeAchiev> countBadge() {
		
		return null;
	}

}
