package com.ctv.quizup.statistics.badge;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchLog;
import com.ctv.quizup.match.model.MatchQuestionLog;
import com.ctv.quizup.match.model.MatchResult;

import com.ctv.quizup.user.model.Badge;
import com.ctv.quizup.user.model.BadgeAchiev;
import com.ctv.quizup.statistics.badge.BadgeComputeUtils;

public class BadgeComputeContext {
	public static interface BadgeVisitor {
		
		public BadgeAchiev visitBadge(BadgeAchiev achiev);
		public BadgeAchiev visitBadge(BadgeAchiev achiev, BadgeContext context);
		public BadgeAchiev visitBadge(BadgeAchiev achiev, BadgeContext context, BadgeRequest request);
	};
	
	
	

	
	public static final String[] BADGE_LIST = {
		BadgeComputeUtils.BADGE_NEWMEMBER,
		BadgeComputeUtils.BADGE_TURTLE,
		BadgeComputeUtils.BADGE_TOTALBLACK,
		BadgeComputeUtils.BADGE_LOOP,
		BadgeComputeUtils.BADGE_FLIGHT,
		BadgeComputeUtils.BADGE_MASTER,
		BadgeComputeUtils.BADGE_STABLE,
		BadgeComputeUtils.BADGE_POEM,
		BadgeComputeUtils.BADGE_ATLAS,
		BadgeComputeUtils.BADGE_PROFESSOR,
		BadgeComputeUtils.BADGE_ZEN,
		BadgeComputeUtils.BADGE_OLDSOLDIER,
		BadgeComputeUtils.BADGE_GENIUS,
		BadgeComputeUtils.BADGE_REPEAT,
		BadgeComputeUtils.BADGE_GOLDENGOAL,
		BadgeComputeUtils.BADGE_SUPER,
		BadgeComputeUtils.BADGE_HARD,
		BadgeComputeUtils.BADGE_UNDEFEAT,
		BadgeComputeUtils.BADGE_FIGHTER,
		BadgeComputeUtils.BADGE_SAGE
	};
	
	public static Map<String, BadgeVisitor> visitorMap() {
		HashMap<String, BadgeVisitor> visitMap = new HashMap<String, BadgeVisitor>();
		
		visitMap.put(
				BadgeComputeUtils.BADGE_NEWMEMBER, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_TURTLE, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_TOTALBLACK, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_LOOP, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_FLIGHT, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_MASTER, 
				new BadgeComputeUtils.VisitNewMember()
				);

		visitMap.put(
				BadgeComputeUtils.BADGE_STABLE, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_POEM, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_ATLAS, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_PROFESSOR, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_ZEN, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_OLDSOLDIER, 
				new BadgeComputeUtils.VisitNewMember()
				);

		visitMap.put(
				BadgeComputeUtils.BADGE_GENIUS, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_REPEAT, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_GOLDENGOAL, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_SUPER, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_HARD, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_UNDEFEAT, 
				new BadgeComputeUtils.VisitNewMember()
				);

		visitMap.put(
				BadgeComputeUtils.BADGE_FIGHTER, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		visitMap.put(
				BadgeComputeUtils.BADGE_SAGE, 
				new BadgeComputeUtils.VisitNewMember()
				);
		
		
		return visitMap;
	}
	
	public static int updateProgress(int totalXP) {
		int level = 0;
		return level;
	}

	public static BadgeAchiev updateBadgeAchiev(
			BadgeAchiev badgeAchiev, MatchResult result) {

		return badgeAchiev;
	}

	public static BadgeAchiev updateBadgeAchiev(
			BadgeAchiev badgeAchiev, List<MatchQuestionLog> log) {

		return badgeAchiev;
	}
	
	public static BadgeAchiev increaseBadgeProgress(BadgeAchiev badgeAchiev, double incrProgress) {
		
			double newProgress = badgeAchiev.getProgress() + incrProgress;
			badgeAchiev.setProgress(newProgress);
			
			if(newProgress >= 1.0) {
				badgeAchiev.setFinished(true);
			}
			
			badgeAchiev.setModifiedDate(new Date());
		
		return badgeAchiev;
	}
	
	public static BadgeAchiev checkIncrBadgeProgress(BadgeAchiev badgeAchiev, String badgeId, double incrProgress) {
		String badge = badgeAchiev.getBadgeId();
		if(badge.equalsIgnoreCase(badgeId)){
			double newProgress = badgeAchiev.getProgress() + incrProgress;
			badgeAchiev.setProgress(newProgress);
			
			if(newProgress >= 1.0) {
				badgeAchiev.setFinished(true);
			}
			
			badgeAchiev.setModifiedDate(new Date());
		}
		return badgeAchiev;
	}
	
	// ////////////////////////////////////////////////////////////////////////////
	// ABSTRACT-INFORMATION FOR COMPUTING
	// ////////////////////////////////////////////////////////////////////////////
	public static interface BadgeRequest {
		
	};
	
	public static interface BadgeContext {
		public static final int CONTEXT_TOPIC = 1;
		public static final int CONTEXT_MATCH = 2;
		public static final int CONTEXT_QUESTION = 3;
		
		public int getType();
		
		
	}
	
	public static abstract class BadgeBaseContext implements BadgeContext {
		private BadgeAchiev achivement;
		
		public BadgeBaseContext(BadgeAchiev achiev) {
			this.achivement = achiev;
		}
		
		
		
		public String getUserId() {
			if(achivement != null) {
				return this.achivement.getUserId();
			}
			return null;
		}
		
		public String getBadgeId() {
			if(achivement != null) {
				return this.achivement.getBadgeId();
			}
			return null;
		}
		
		public Badge getBadge() {
			if(achivement != null) {
				return this.achivement.getBadge();
			}
			return null;
		}
		
		public BadgeAchiev getAchivement() {
			return achivement;
		}
		public void setAchivement(BadgeAchiev achivement) {
			this.achivement = achivement;
		}
		
	}
	
	public static class BadgeTopicContext extends BadgeBaseContext {
		private MatchBaseInfo matchBaseInfo;
		
		public BadgeTopicContext(BadgeAchiev achiev, MatchBaseInfo matchBase){
			super(achiev);
			this.matchBaseInfo = matchBase;
		}
		
		public String getTopic() {
			if(this.matchBaseInfo != null)
				return this.matchBaseInfo.getTopicId();
			return null;
		}
		
		

		public int getType() {
			return BadgeContext.CONTEXT_TOPIC;
		}
		
		
		
	}
	
	public static class BadgeMatchContext extends BadgeBaseContext {
		private MatchResult matchResult;
		
		
		
		public BadgeMatchContext(BadgeAchiev achiev, MatchResult result) {
			super(achiev);
			this.matchResult = result;
		}
		
		public boolean isMatchFinished() {
			if(matchResult != null) {
				return matchResult.isFinished();
			}
			return false;
		}
		
		public int getMatchResult() {
			if(matchResult != null) {
				return matchResult.getResult();
			}
			
			return 0;
		}

		public int getType() {
			return BadgeContext.CONTEXT_MATCH;
		}
		
	}
	
	public static class BadgeQuestionContext extends BadgeBaseContext {
		private MatchLog log;
		
		public BadgeQuestionContext(BadgeAchiev achiev, MatchLog log) {
			super(achiev);
			this.log = log;
		}
		
		public List<MatchQuestionLog> getQuestionLog() {
			if(log != null) {
				return log.getQuesLog();
			}
			return null;
		}

		public int getType() {
			return BadgeContext.CONTEXT_QUESTION;
		}
		
	}
	
	
}
