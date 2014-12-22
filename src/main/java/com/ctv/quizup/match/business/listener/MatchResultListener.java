package com.ctv.quizup.match.business.listener;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.business.SimpleMatchRedis;
import com.ctv.quizup.match.model.MatchResult;

public abstract class MatchResultListener extends RedisListener {
	public void process(String message) {
		ObjectMapper mapper = new ObjectMapper(); 	
		try {
			MatchResult match = mapper.readValue(message, MatchResult.class);
			this.process(match);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public abstract void process(MatchResult match);
	
	public void subscribeMatch() throws java.lang.InterruptedException {
		this.subscribeChannel(SimpleMatchRedis.MATCH_RESULT_CHANNEL);
	}
}
