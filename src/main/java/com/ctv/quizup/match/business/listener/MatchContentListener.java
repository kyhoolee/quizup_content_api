package com.ctv.quizup.match.business.listener;

import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.match.business.SimpleMatchRedis;
import com.ctv.quizup.match.model.MatchContent;

public abstract class MatchContentListener extends RedisListener {
	public void process(String message) {
		ObjectMapper mapper = new ObjectMapper(); 	
		try {
			MatchContent match = mapper.readValue(message, MatchContent.class);
			this.process(match);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public abstract void process(MatchContent match);
	
	public void subscribeContent() throws java.lang.InterruptedException {
		this.subscribeChannel(SimpleMatchRedis.MATCH_CONTENT_CHANNEL);
	}
}
