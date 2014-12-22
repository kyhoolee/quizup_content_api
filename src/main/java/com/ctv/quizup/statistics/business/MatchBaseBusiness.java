package com.ctv.quizup.statistics.business;

import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchResult;

public class MatchBaseBusiness {
	
	MatchProcess matchProcess;
	
	private MatchBaseInfo matchBase;
	private MatchLog firstLog;
	private MatchLog secondLog;
	private MatchResult matchResult;
	
	public MatchBaseBusiness() {
		this.matchProcess = new MatchProcess();
	}
	
	public MatchBaseBusiness(String matchId) {
		this.matchProcess = new MatchProcess();
		this.initProcess(matchId);
	}
	

	public void setMatchProcess(MatchProcess matchProcess) {
		this.matchProcess = matchProcess;
	}


	public MatchBaseInfo getMatchBase() {
		return matchBase;
	}


	public void setMatchBase(MatchBaseInfo matchBase) {
		this.matchBase = matchBase;
	}


	public MatchLog getFirstLog() {
		return firstLog;
	}


	public void setFirstLog(MatchLog firstLog) {
		this.firstLog = firstLog;
	}


	public MatchLog getSecondLog() {
		return secondLog;
	}


	public void setSecondLog(MatchLog secondLog) {
		this.secondLog = secondLog;
	}


	public MatchResult getMatchResult() {
		return matchResult;
	}


	public void setMatchResult(MatchResult matchResult) {
		this.matchResult = matchResult;
	}



	
	
	
	public void initProcess(String matchId) {
		this.matchBase = this.matchProcess.getMatchBaseInfo(matchId);
		this.firstLog = this.matchProcess.getFirstMatchLog(matchId);
		this.secondLog = this.matchProcess.getSecondMatchLog(matchId);
		this.matchResult = this.matchProcess.getMatchResult(matchId);
	}
	

}
