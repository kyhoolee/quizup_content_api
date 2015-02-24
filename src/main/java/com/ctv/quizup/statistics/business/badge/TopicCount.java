package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.statistics.business.LevelBusiness;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.UserTopicLevel;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;

public class TopicCount extends BaseCount {
	// Choose Topic
	public static final String LITERATURE = "4";
	public static final String ATLAS = "10";

	public static final String PAINT = "6";
	public static final String MATHEMATICS = "11";
	public static final String PHYSICS = "11";
	public static final String CHEMICAL = "13";

	// Choose parent-topic
	public static final String MUSIC = "1";
	public static final String ENTERTAINMENT = "9";
	public static final String SPORT = "13";
	public static final String LIFE_STYLE = "6";

	public static final String NATURE = "13";

	public TopicCount(BadgeBusiness business) {
		super(business);
	}

	@Override
	public void count() {
		String matchId = this.getBadgeBusiness().getMatchBase().getMatchId();
		String firstId = this.getBadgeBusiness().getMatchBase()
				.getFirstUserId();
		String secondId = this.getBadgeBusiness().getMatchBase()
				.getSecondUserId();
	}

	public List<BadgeAchiev> countTopicAchiev(String userId, String matchId) {

		String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();
		TopicRedis topicProcess = new TopicRedis();
		Topic topic = topicProcess.getTopicById(topicId);
		String categoryId = topic.getParentId();

		return this.countTopicCategoryAchiev(userId, categoryId, topicId);
	}

	public List<BadgeAchiev> countTopicCategoryAchiev(String userId,
			String categoryId, String topicId) {
		List<BadgeAchiev> result = new ArrayList<BadgeAchiev>();

		if (categoryId.equalsIgnoreCase(MUSIC)) {
			result.addAll(this.countAchievMusicTopic(userId));
		} else if (categoryId.equalsIgnoreCase(ENTERTAINMENT)) {
			result.addAll(this.countAchievEntertainmentTopic(userId));
		} else if (categoryId.equalsIgnoreCase(SPORT)) {
			result.addAll(this.countAchievSportTopic(userId));
		} else if (categoryId.equalsIgnoreCase(LIFE_STYLE)) {
			result.addAll(this.countAchievLifeStyleTopic(userId));
		}
		
		if (topicId.equalsIgnoreCase(LITERATURE)) {
			result.addAll(this.countAchievPoemTopic(userId));
		} else if (topicId.equalsIgnoreCase(ATLAS)) {
			result.addAll(this.countAchievMapTopic(userId));
		} else if (topicId.equalsIgnoreCase(PAINT)) {
			result.addAll(this.countAchievPainterTopic(userId));
		} else if (topicId.equalsIgnoreCase(MATHEMATICS)) {
			result.addAll(this.countAchievMathematicanTopic(userId));
		} else if (topicId.equalsIgnoreCase(PHYSICS)) {
			result.addAll(this.countAchievPhysicanTopic(userId));
		} else if (topicId.equalsIgnoreCase(CHEMICAL)) {
			result.addAll(this.countAchievChemicanTopic(userId));
		}

		return result;
	}

	public List<BadgeAchiev> countAchievMusicTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Singer);
	}

	public List<BadgeAchiev> countAchievSportTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Fan);
	}

	public List<BadgeAchiev> countAchievEntertainmentTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Artist);
	}

	public List<BadgeAchiev> countAchievNatureTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Naturist);
	}

	public List<BadgeAchiev> countAchievLifeStyleTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_LifeStylist);
	}

	//////////////////////////////////////////////////////////////////////////////////
	
	public List<BadgeAchiev> countAchievPoemTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Poem);
	}

	public List<BadgeAchiev> countAchievMapTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Map);
	}

	public List<BadgeAchiev> countAchievPainterTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Painter);
	}

	public List<BadgeAchiev> countAchievMathematicanTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Mathematican);
	}

	public List<BadgeAchiev> countAchievPhysicanTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Physist);
	}

	public List<BadgeAchiev> countAchievChemicanTopic(String userId) {
		return this.countAchievBadge(userId, CountType.Topic_Chemican);
	}
	
	
	@Override
	public List<BadgeAchiev> countBadge() {

		return null;
	}

	@Override
	public Map<String, List<BadgeAchiev>> countUserBadge() {
		Map<String, List<BadgeAchiev>> result = new HashMap<String, List<BadgeAchiev>>();

		String matchId = this.getBadgeBusiness().getMatchBase().getMatchId();
		String firstId = this.getBadgeBusiness().getMatchBase()
				.getFirstUserId();
		String secondId = this.getBadgeBusiness().getMatchBase()
				.getSecondUserId();

		result.put(firstId, this.countTopicAchiev(firstId, matchId));
		result.put(secondId, this.countTopicAchiev(secondId, matchId));

		return result;
	}
}
