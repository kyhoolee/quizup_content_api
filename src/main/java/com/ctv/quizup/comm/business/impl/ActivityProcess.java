package com.ctv.quizup.comm.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ctv.quizup.comm.model.Activity;
import com.ctv.quizup.comm.model.Challenge;
import com.ctv.quizup.comm.redis.ActivityRedis;
import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.match.model.MatchResult;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.model.UserBadgeAchiev;
import com.ctv.quizup.user.model.UserBaseInfo;

public class ActivityProcess {
	ActivityRedis activityRedis;

	public ActivityProcess() {
		this.activityRedis = new ActivityRedis();
	}

	public void writeActivity(Activity activity) {
		this.activityRedis.writeActivityToHashes(activity.toString(),
				activity.getActivityId());
		this.activityRedis.writeActivityToSorted(activity.getCreatedDate()
				.getTime(), activity.getActivityId(), activity.getAuthorId());

	}

	public Activity getActivity(String activityId) {
		Activity activity = this.activityRedis.getActivity(activityId);
		return activity;
	}

	public List<Activity> getUserActivity(String userId, long start, long end) {
		List<Activity> actList = new ArrayList<Activity>();

		actList = this.activityRedis.getUserActListByIndex(userId, start, end);

		return actList;
	}

	public static enum ActivityType {
		Challenge, MatchResult, BadgeAchievement
	}
	
	public void writeChallengeActivity(Challenge challenge) {
		List<Activity> actList = this.getChallengeAcitivty(challenge);
		
		for(Activity act : actList) {
			this.writeActivity(act);
		}
	}

	public List<Activity> getChallengeAcitivty(Challenge challenge) {
		List<Activity> actList = new ArrayList<Activity>();

		Activity first = new Activity(challenge.getUserId(),
				challenge.getCreatedDate());
		first.setType(ActivityType.Challenge);
		Activity second = new Activity(challenge.getRivalId(),
				challenge.getCreatedDate());
		second.setType(ActivityType.Challenge);

		UserServiceProcess userService = new UserServiceProcess();
		UserBaseInfo firstUser = userService.getUser(first.getAuthorId());
		UserBaseInfo secondUser = userService.getUser(second.getAuthorId());

		String firstDes = "Bạn thách đấu " + secondUser.getUserName();
		String secondDes = firstUser.getUserName() + " thách đấu bạn";

//		first.setDescription(firstDes);
//		second.setDescription(secondDes);
		first.getContent().setAction("challenge");
		first.getContent().setSubject(firstUser);
		first.getContent().setObject(secondUser);
		
		first.getContent().setAction("challenged");
		first.getContent().setSubject(secondUser);
		first.getContent().setObject(firstUser);

		actList.add(first);
		actList.add(second);

		return actList;
	}

	public Activity getSecondChallengeAcitivty(Challenge challenge) {
		Activity activity = new Activity(challenge.getRivalId());

		return activity;
	}

	public Activity getFirstChallengeAcitivty(Challenge challenge) {
		Activity activity = new Activity(challenge.getUserId());

		return activity;
	}
	
	public void writeMatchActivity(MatchResult matchResult) {
		List<Activity> actList = this.getMatchActivity(matchResult);
		for(Activity act : actList) {
			this.writeActivity(act);
		}
	}

	public List<Activity> getMatchActivity(MatchResult matchResult) {
		List<Activity> actList = new ArrayList<Activity>();
		MatchProcess matchProcess = new MatchProcess();
		MatchBaseInfo matchBase = matchProcess.getMatchBaseInfo(matchResult.getMatchId());

		Activity first = new Activity(matchBase.getFirstUserId(), matchResult.getCreatedDate(), ActivityType.MatchResult);
		Activity second = new Activity(matchBase.getSecondUserId(), matchResult.getCreatedDate(), ActivityType.MatchResult);


		UserServiceProcess userService = new UserServiceProcess();
		UserBaseInfo firstUser = userService.getUser(first.getAuthorId());
		UserBaseInfo secondUser = userService.getUser(second.getAuthorId());

		first.getContent().setSubject(firstUser);
		first.getContent().setObject(secondUser);
		
		second.getContent().setSubject(secondUser);
		second.getContent().setObject(firstUser);
		
		String firstDes = "";//"Bạn thách đấu " + secondUser.getUserName();
		String secondDes = "";//firstUser.getUserName() + " thách đấu bạn";
		switch(matchResult.getResult()) {
		case 1 :
			firstDes = "Bạn thắng " + secondUser.getUserName();
			secondDes = "Bạn thua " + firstUser.getUserName();
			
			first.getContent().setAction("win");
			second.getContent().setAction("lose");
			
			break;
			
		case 0 :
			firstDes = "Bạn hòa " + secondUser.getUserName();
			secondDes = "Bạn hòa " + firstUser.getUserName();
			
			first.getContent().setAction("tie");
			second.getContent().setAction("tie");
			
			break;
			
		case -1:
			firstDes = "Bạn thua " + secondUser.getUserName();
			secondDes = "Bạn thắng " + firstUser.getUserName();
			
			first.getContent().setAction("lose");
			second.getContent().setAction("win");
			
			break;
		}
		
//		first.setDescription(firstDes);
//		second.setDescription(secondDes);

		actList.add(first);
		actList.add(second);

		return actList;
	}

	public Activity getFirstMatchAcitivty(MatchResult matchResult) {
		Activity activity = null;

		return activity;
	}

	public Activity getSecondMatchActivity(MatchResult matchResult) {
		Activity activity = null;

		return activity;
	}

	
	public Activity getAchievementAcitivty(UserBadgeAchiev achiev) {
		Activity activity = null;

		return activity;
	}

}
