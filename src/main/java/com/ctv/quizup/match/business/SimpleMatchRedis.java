package com.ctv.quizup.match.business;


import java.util.Date;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.ctv.quizup.match.model.Match;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchContent;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.redis.RedisPool;

/**
 * Write MatchData to log file and redisPubSub
 * LogFile for batch processing
 * RedisPubSub for event-based process
 * 
 * @author kyhoolee
 *
 */
public class SimpleMatchRedis {
	static Logger log = Logger.getLogger(SimpleMatchRedis.class.getName());
	
	public static final String MATCH_BASE_INFO_CHANNEL = "match_base_info_channel";
	public static final String MATCH_CONTENT_CHANNEL = "match_content_channel";
	public static final String MATCH_LOG_CHANNEL = "match_log_channel";
	public static final String MATCH_RESULT_CHANNEL = "match_result_channel";
	
	public void publishAndWrite(Object match) {
		
	}
	
	public void publishWriteMatchBase(MatchBaseInfo match) {
		this.publishMatchBaseInfo(match);
		this.writeMatchBaseToLog(match);
	}
	
	public void publishWriteMatchContent(MatchContent match) {
		this.publishMatchContent(match);
		this.writeMatchContentToLog(match);
	}
	
	public void publishWriteMatchLog(MatchLog match) {
		this.publishMatchLog(match);
		this.writeMatchLogToLog(match);
	}
	
	public void publishWriteMatchResult(MatchResult match) {
		this.publishMatchResult(match);
		this.writeMatchResultToLog(match);
	}
	
	private String format(String id, String content) {
		return new Date().toString() + "\t" + id + "\t" + content;
	}
	
	public void writeMatchToLog(Match match) {
		log.debug(this.format("Match", match.toString()));
	}
	
	public void writeMatchBaseToLog(MatchBaseInfo match) {
		log.debug(this.format("MatchBaseInfo", match.toString()));
		//(new Date().toString() + "\tMatchBaseInfo\t" + match.toString());
	}
	
	public void writeMatchContentToLog(MatchContent match) {
		log.debug(this.format("MatchContent", match.toString()));
		//log.debug(new Date().toString() + "\tMatchContent\t" + match.toString());
	}
	
	public void writeMatchLogToLog(MatchLog match) {
		log.debug(this.format("MatchLog", match.toString()));
		//log.debug(new Date().toString() + "\tMatchLog\t" + match.toString());
		
	}
	
	public void writeMatchResultToLog(MatchResult match) {
		log.debug(this.format("MatchResult", match.toString()));
		//log.debug(new Date().toString() + "\tMatchResult\t" + match.toString());
	}
	
	public void publishMatchBaseInfo(MatchBaseInfo match) {
		String data = match.toString();
		
		Jedis jedis = RedisPool.getIntance().getResource();
		
		
		jedis.publish(MATCH_BASE_INFO_CHANNEL, data);
	}
	
	public void publishMatchContent(MatchContent match) {
		String data = match.toString();
		
		Jedis jedis = RedisPool.getJedis();
		
		
		jedis.publish(MATCH_CONTENT_CHANNEL, data);
	}	
	public void publishMatchLog(MatchLog match) {
		String data = match.toString();
		
		Jedis jedis = RedisPool.getJedis();
		
		
		jedis.publish(MATCH_LOG_CHANNEL, data);
	}	
	public void publishMatchResult(MatchResult match) {
		String data = match.toString();
		
		Jedis jedis = RedisPool.getJedis();
		
		
		jedis.publish(MATCH_RESULT_CHANNEL, data);
	}	
	

}
