package com.ctv.quizup.user.business.impl;

import java.util.List;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.user.model.UserPlayStats;
import com.ctv.quizup.user.redis.UserStatsRedis;

public class UserPlayStatsProcess {
	UserStatsRedis statsRedis;
	MatchProcess matchProcess;

	public UserPlayStatsProcess() {
		this.statsRedis = new UserStatsRedis();
		this.matchProcess = new MatchProcess();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// INCREASE-STATS-VALUES
	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public void incrTotalMatch(String userId) {
		this.statsRedis.incrTotalMatch(userId);
	}

	public void incrWinMatch(String userId) {
		this.statsRedis.incrWinMatch(userId);
	}

	public void incrLoseMatch(String userId) {
		this.statsRedis.incrLoseMatch(userId);
	}

	public void incrTieMatch(String userId) {
		this.statsRedis.incrTieMatch(userId);
	}

	public void incrMatchPoint(String userId, int addPoint) {
		this.statsRedis.incrMatchPoint(userId, addPoint);
	}

	public void incrAnswerTime(String userId, int addTime) {
		this.statsRedis.incrAnswerTime(userId, addTime);
	}

	public UserPlayStats getUserPlayStats(String userId) {
		return this.statsRedis.getUserPlayStats(userId);
	}

	public void updatePlayStats(MatchResult match) {
		MatchBaseInfo matchBase = this.matchProcess.getMatchBaseInfo(match
				.getMatchId());
		String firstId = matchBase.getFirstUserId();
		String secondId = matchBase.getSecondUserId();

		this.incrTotalMatch(firstId);
		this.incrTotalMatch(secondId);

		int result = match.getResult();
		switch (result) {
		case 0:
			this.incrTieMatch(firstId);
			this.incrTieMatch(secondId);
			break;
		case 1:
			this.incrWinMatch(firstId);
			this.incrLoseMatch(secondId);
			break;
		case -1:
			this.incrLoseMatch(firstId);
			this.incrWinMatch(secondId);
			break;
		}

		this.incrByMatchLog(match.getMatchId(), firstId,
				matchProcess.getFirstMatchLog(match.getMatchId()).getQuesLog());
		this.incrByMatchLog(match.getMatchId(), secondId,
				matchProcess.getSecondMatchLog(match.getMatchId()).getQuesLog());
	}

	public void incrByMatchLog(String matchId, String userId, List<MatchQuestionLog> log) {
		int addPoint = 0;
		int addTime = 0;

		for (MatchQuestionLog quesLog : log) {
			if(quesLog.getMatchId().equalsIgnoreCase(matchId)) {
				addPoint += quesLog.getPoint();
				addTime += quesLog.getTime();
			}
		}

		this.incrAnswerTime(userId, addTime);
		this.incrMatchPoint(userId, addPoint);
	}
}
