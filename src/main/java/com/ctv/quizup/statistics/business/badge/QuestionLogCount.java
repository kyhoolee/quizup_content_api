package com.ctv.quizup.statistics.business.badge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.TopicLevelCompute;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;

public class QuestionLogCount extends BaseCount {

	public QuestionLogCount(BadgeBusiness business) {
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

		if(firstLog.size() == TopicLevelCompute.MATCH_LEN 
				&& secondLog.size() == TopicLevelCompute.MATCH_LEN) {
			// COUNT - COUNTER-MATCH
			this.countCounterMatch(firstId, secondId, firstLog, secondLog);
			
			// COUNT - QUALITY-MATCH
			this.countByAnswerCase(firstId, firstLog);
			this.countByAnswerCase(secondId, secondLog);
		}
		
		

	}
	
	public void countCounterMatch(String firstId, String secondId, 
			List<MatchQuestionLog> firstLog, List<MatchQuestionLog> secondLog) {
		
		int firstPoint = 0;
		int secondPoint = 0;
		
		for(int i = 0 ; i < firstLog.size() - 1 ; i ++) {
			firstPoint += firstLog.get(i).getPoint();
			secondPoint += secondLog.get(i).getPoint();
		}
		
		if(firstPoint < secondPoint) {
			firstPoint += firstLog.get(firstLog.size() - 1).getPoint();
			secondPoint += secondLog.get(secondLog.size() - 1).getPoint();
			
			if(secondPoint < firstPoint) {
				this.countCounterAnswer(firstId);
			}
		} else {
			firstPoint += firstLog.get(firstLog.size() - 1).getPoint();
			secondPoint += secondLog.get(secondLog.size() - 1).getPoint();
			
			if(secondPoint > firstPoint) {
				this.countCounterAnswer(secondId);
			}
		}
		
	}
	
	public void countCounterMatchAchiev(Map<String, List<BadgeAchiev>> achievMap, String firstId, String secondId, 
			List<MatchQuestionLog> firstLog, List<MatchQuestionLog> secondLog) {
		//Map<String, List<UserBadgeAchiev>> result = new HashMap<String, List<UserBadgeAchiev>>();
		
		int firstPoint = 0;
		int secondPoint = 0;
		
		for(int i = 0 ; i < firstLog.size() - 1 ; i ++) {
			firstPoint += firstLog.get(i).getPoint();
			secondPoint += secondLog.get(i).getPoint();
		}
		
		if(firstPoint < secondPoint) {
			firstPoint += firstLog.get(firstLog.size() - 1).getPoint();
			secondPoint += secondLog.get(secondLog.size() - 1).getPoint();
			
			if(secondPoint < firstPoint) {
				List<BadgeAchiev> achievs = this.countCounterAnswerAchiev(firstId);
				this.addAchiev(achievMap, firstId, achievs);
			}
		} else {
			firstPoint += firstLog.get(firstLog.size() - 1).getPoint();
			secondPoint += secondLog.get(secondLog.size() - 1).getPoint();
			
			if(secondPoint > firstPoint) {
				List<BadgeAchiev> achievs = this.countCounterAnswerAchiev(secondId);
				this.addAchiev(achievMap, secondId, achievs);
				
			}
		}
		
		//return result;
		
		
	}
	
	public int countByAnswerCase(String userId, List<MatchQuestionLog> log) {
		int anCase = 0;
		
		int numAnswer = 0;
		for(MatchQuestionLog ques : log) {
			if(ques.getPoint() > 0) {
				numAnswer ++;
			}
		}
		
		switch(numAnswer) {
		case 0 :
			this.countZeroAnswer(userId);
			break;
		case 1 :
			this.countOneAnswer(userId);
			break;
		case 5 :
			this.countGoodAnswer(userId);
			break;
		case 6 :
			this.countGoodAnswer(userId);
			break;
		case 7 :
			this.countGoodAnswer(userId);
			this.countPerfectAnswer(userId);
			break;
		
		}
		
		return anCase;
	}
	
	public List<BadgeAchiev> countByAnswerCaseAchiev(String userId, List<MatchQuestionLog> log) {
		List<BadgeAchiev> result = new ArrayList<BadgeAchiev>();
		
		int numAnswer = 0;
		for(MatchQuestionLog ques : log) {
			if(ques.getPoint() > 0) {
				numAnswer ++;
			}
		}
		
		switch(numAnswer) {
		case 0 :
			result = this.countZeroAnswerAchiev(userId);
			break;
		case 1 :
			result = this.countOneAnswerAchiev(userId);
			break;
		case 5 :
			result = this.countGoodAnswerAchiev(userId);
			break;
		case 6 :
			result = this.countGoodAnswerAchiev(userId);
			break;
		case 7 :
			result = this.countGoodAnswerAchiev(userId);
			result.addAll(this.countPerfectAnswerAchiev(userId));
			break;
		
		}
		return result;
	}

	public void countUserMatchBadge(String userId, int answerCase) {
		
		switch (answerCase) {
		case 0:
			// Count zero-answer-match
			this.countZeroAnswer(userId);
			break;
		case 1:
			// Count one-answer-match
			this.countOneAnswer(userId);
			break;
		case 2:
			// Count good-match
			this.countGoodAnswer(userId);
			break;
		case 3:
			// Count perfect-match
			this.countPerfectAnswer(userId);
			break;
		case 4:
			// Count counter-answer-match
			this.countCounterAnswer(userId);
			break;
			
		
		}

	}

	public void countZeroAnswer(String userId) {
		this.countBadge(userId, CountType.Match_0_Answer);
	}

	public void countOneAnswer(String userId) {
		this.countBadge(userId, CountType.Match_1_Answer);
	}

	public void countGoodAnswer(String userId) {
		this.countBadge(userId, CountType.Match_Good);
	}

	public void countPerfectAnswer(String userId) {
		this.countBadge(userId, CountType.Match_Perfect);
	}
	
	public void countCounterAnswer(String userId) {
		this.countBadge(userId, CountType.Match_Counter);
	}
	
	public List<BadgeAchiev> countCounterAnswerAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Counter);
	}
	
	public List<BadgeAchiev> countZeroAnswerAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_0_Answer);
	}

	public List<BadgeAchiev> countOneAnswerAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_1_Answer);
	}

	public List<BadgeAchiev> countGoodAnswerAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Good);
	}

	public List<BadgeAchiev> countPerfectAnswerAchiev(String userId) {
		return this.countAchievBadge(userId, CountType.Match_Perfect);
	}
	

	@Override
	public List<BadgeAchiev> countBadge() {

		return null;
	}

	@Override
	public Map<String, List<BadgeAchiev>> countUserBadge() {
		Map<String, List<BadgeAchiev>> achievMap = new HashMap<String, List<BadgeAchiev>>();
		
		String firstId = this.getBadgeBusiness().getMatchBase().getFirstUserId();
		List<MatchQuestionLog> firstLog = this.getBadgeBusiness().getFirstLog().getQuesLog();
		
		String secondId = this.getBadgeBusiness().getMatchBase().getSecondUserId();
		List<MatchQuestionLog> secondLog = this.getBadgeBusiness().getSecondLog().getQuesLog();

		// COUNT - BADGE
		
		if(firstLog.size() == TopicLevelCompute.MATCH_LEN 
				&& secondLog.size() == TopicLevelCompute.MATCH_LEN) {
			// COUNT - COUNTER-MATCH
			this.countCounterMatchAchiev(achievMap, firstId, secondId, firstLog, secondLog);
			
			// COUNT - QUALITY-MATCH
			List<BadgeAchiev> fAchievs = this.countByAnswerCaseAchiev(firstId, firstLog);
			this.addAchiev(achievMap, firstId, fAchievs);
			
			List<BadgeAchiev> sAchievs = this.countByAnswerCaseAchiev(secondId, secondLog);
			this.addAchiev(achievMap, secondId, sAchievs);
		}
		
		return achievMap;
	}
}
