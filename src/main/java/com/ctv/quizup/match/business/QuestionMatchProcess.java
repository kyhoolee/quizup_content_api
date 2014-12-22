package com.ctv.quizup.match.business;

import com.ctv.quizup.match.business.listener.MatchBaseListener;
import com.ctv.quizup.match.model.MatchBaseInfo;

public class QuestionMatchProcess {
	QuestionMatchBaseListener matchBaseListener;
	
	public QuestionMatchProcess() {
		this.matchBaseListener = new QuestionMatchBaseListener();
	}
	
	public void start() {
		try {
			this.matchBaseListener.subscribeMatch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		UserMatchProcess process = new UserMatchProcess();
		process.start();
	}
	
	private class QuestionMatchBaseListener extends MatchBaseListener{
		@Override
		public void process(MatchBaseInfo message) {
			
		} 
		
	}
}
