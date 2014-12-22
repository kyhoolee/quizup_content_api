package com.ctv.quizup.match.business.listener;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.business.SimpleMatchRedis;
import com.ctv.quizup.match.model.MatchBaseInfo;

public abstract class MatchBaseListener extends RedisListener {
	
	public void process(String message) {
		ObjectMapper mapper = new ObjectMapper(); 	
		try {
			MatchBaseInfo match = mapper.readValue(message, MatchBaseInfo.class);
			this.process(match);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public abstract void process(MatchBaseInfo match);
	
	public void subscribeMatch() throws java.lang.InterruptedException {
		this.subscribeChannel(SimpleMatchRedis.MATCH_BASE_INFO_CHANNEL);
	}
}
