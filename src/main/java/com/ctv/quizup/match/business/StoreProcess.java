package com.ctv.quizup.match.business;

import java.sql.SQLException;
import java.util.List;

import com.ctv.quizup.match.model.Match;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.sql.MatchSQL;

public class StoreProcess {
	MatchProcess matchRedis;
	MatchSQL matchSQL;
	
	public StoreProcess() {
		this.matchRedis = new MatchProcess();
		this.matchSQL = new MatchSQL();
		
	}
	
	public void moveMatch(String matchId) {
		Match match = this.matchRedis.getMatch(matchId);
		try {
			this.matchSQL.insertMatch(match);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		this.matchRedis.removeMatch(matchId);
		
	}
	
	public void moveMatchBatch(long startTime, long endTime) {
		List<MatchBaseInfo> baseList = this.matchRedis.getMatchBaseRedis().removeMatchs(startTime, endTime);
		
		for(MatchBaseInfo base : baseList) {
			this.moveMatch(base.getMatchId());
		}
	}

}
