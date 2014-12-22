package com.ctv.quizup.match.business.listener;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.business.SimpleMatchRedis;
import com.ctv.quizup.match.model.MatchLog;

public abstract class MatchLogListener extends RedisListener {
	public void process(String message) {
		ObjectMapper mapper = new ObjectMapper(); 	
		try {
			MatchLog match = mapper.readValue(message, MatchLog.class);
			this.process(match);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public abstract void process(MatchLog match);
	
	public void subscribeMatch() throws java.lang.InterruptedException {
		this.subscribeChannel(SimpleMatchRedis.MATCH_LOG_CHANNEL);
	}
}
