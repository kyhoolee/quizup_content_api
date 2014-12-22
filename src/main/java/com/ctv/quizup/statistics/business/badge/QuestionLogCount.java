package com.ctv.quizup.statistics.business.badge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.statistics.business.BadgeBusiness;
import com.ctv.quizup.user.model.UserBadgeAchiev;
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

		String topicId = this.getBadgeBusiness().getMatchBase().getTopicId();

		int result = this.getBadgeBusiness().getMatchResult().getResult();

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

	@Override
	public List<UserBadgeAchiev> countBadge() {

		return null;
	}
}
