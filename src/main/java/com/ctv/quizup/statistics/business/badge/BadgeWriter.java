package com.ctv.quizup.statistics.business.badge;

import com.ctv.quizup.user.model.BadgeCountInfo;
import com.ctv.quizup.user.model.BadgeCountInfo.CountType;
import com.ctv.quizup.user.redis.BadgeRedis;

/**
 * Write Badges into Database
 * @author kyhoolee
 *
 */

public class BadgeWriter {
	private BadgeRedis badgeRedis;
	
	public BadgeWriter() {
		this.badgeRedis = new BadgeRedis();
		
	}
	
	public void writeBadge() {
		
	}
	
	public void writeMatchBadge() {
		
	}
	
	public void writeLogBadge() {
		
	}
	
	public void writeTopicBadge() {
		
	}

}
