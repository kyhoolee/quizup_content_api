package com.ctv.quizup.comm.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ctv.quizup.comm.model.FeedBack;
import com.ctv.quizup.comm.redis.FeedBackRedis;
import com.ctv.quizup.util.LoggerUtil;

public class FeedBackProcess {
	public static Logger logger = LoggerUtil.getDailyLogger("FeedbackProcess" + "_error");
	
	
	FeedBackRedis feedRedis;
	
	public FeedBackProcess() {
		this.feedRedis = new FeedBackRedis();
	}
	
	
	public void writeFeedBack(FeedBack feedBack) {
		logger.info("FeedBack : " + feedBack);
		this.feedRedis.writeFeedBackToHashes(feedBack.getFeedBackId(), feedBack.toString());
		this.feedRedis.writeFeedBackToSorted(feedBack.getCreatedDate().getTime(), feedBack.getFeedBackId(), feedBack.getTargetType());
	}
	
	public List<FeedBack> readFeedBack(String targetType, int start, int end) { 
		logger.info("TargetType : " + targetType + " -- " + start + "--" + end);
		List<FeedBack> feedList = new ArrayList<FeedBack>();
		
		feedList = this.feedRedis.getTargetFeedBack(targetType, start, end);
		
		
		return feedList;
	}
	
}
