package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;

public class FrequencyCount extends BaseCount {
	

	public FrequencyCount(BadgeBusiness business) {
		super(business);
	}


	@Override
	public void count() {
		String firstId = this.getBadgeBusiness().getMatchBase()
				.getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog()
				.getQuesLog();

		String secondId = this.getBadgeBusiness().getMatchBase()
				.getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness()
				.getSecondLog().getQuesLog();

		String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();

		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		switch (result) {
		case 1 :
			this.countSuperMan(firstId);
			this.resetSuperMan(secondId);
			break;
		case 0 :
			this.resetSuperMan(firstId);
			this.resetSuperMan(secondId);
			break;
		case -1:
			this.countSuperMan(secondId);
			this.resetSuperMan(firstId);
			break;
		}

	}
	





	public void resetSuperMan(String userId) {
		this.resetBadge(userId, CountType.Frequent_Win);
	}
	public void countSuperMan(String userId) {
		this.countBadge(userId, CountType.Frequent_Win);
	}
	public List<BadgeAchiev> countSuperBadge(String userId) {
		return this.countAchievBadge(userId, CountType.Frequent_Win);
	}
	

	public void resetLoop(String userId) {
		this.resetBadge(userId, CountType.Frequent_Answer);
	}
	public void countLoop(String userId) {
		this.countBadge(userId, CountType.Frequent_Answer);
	}
	public List<BadgeAchiev> countLoopBadge(String userId) {
		return this.countAchievBadge(userId, CountType.Frequent_Answer);
	}
	
	

	@Override
	public List<BadgeAchiev> countBadge() {

		return null;
	}


	@Override
	public Map<String, List<BadgeAchiev>> countUserBadge() {
		Map<String, List<BadgeAchiev>> badges = new HashMap<String, List<BadgeAchiev>>();
		
		List<BadgeAchiev> first = new ArrayList<BadgeAchiev>();
		List<BadgeAchiev> second = new ArrayList<BadgeAchiev>();
		
		String firstId = this.getBadgeBusiness().getMatchBase()
				.getFirstUserId();

		String secondId = this.getBadgeBusiness().getMatchBase()
				.getSecondUserId();

		int result = this.getBadgeBusiness().getMatchResult().getResult();
		
		switch (result) {
		case 1 :
			first.addAll(this.countSuperBadge(firstId));
			this.resetSuperMan(secondId);
			break;
		case 0 :
			this.resetSuperMan(firstId);
			this.resetSuperMan(secondId);
			break;
		case -1:
			second.addAll(this.countSuperBadge(secondId));
			this.resetSuperMan(firstId);
			break;
		}
		
		badges.put(firstId, first);
		badges.put(secondId, second);
		
		return null;
	}

}
