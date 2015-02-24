package com.ctv.quizup.comm.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.comm.model.Challenge;
import com.ctv.quizup.comm.redis.ChallengeRedis;
import com.ctv.quizup.content.model.Topic;
import com.ctv.quizup.content.redis.TopicRedis;
import com.ctv.quizup.match.business.MatchProcess;
import com.ctv.quizup.match.model.MatchBaseInfo;
import com.ctv.quizup.user.business.impl.UserBaseProcess;
import com.ctv.quizup.user.business.impl.UserServiceProcess;
import com.ctv.quizup.user.business.impl.LeaderBoardProcess.TopicUserScore;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.util.LoggerUtil;

public class ChallengeProcess {
	public static Logger logger = LoggerUtil.getDailyLogger("ChallengeProcess" + "_error");
	
	ChallengeRedis challRedis;
	
	public ChallengeProcess() {
		this.challRedis = new ChallengeRedis();
	}
	
	/**
	 * 
	 * @param userId
	 * @param rivalId
	 * @param topicId
	 * @param matchId
	 */
	public Challenge createChallenge(String userId, String rivalId, String topicId, String matchId) {
		Challenge challenge = new Challenge(topicId, matchId, userId, rivalId);
		
		this.challRedis.updateChallenge(challenge.toString(), challenge.getChallengeId());
		this.challRedis.writeSenderChallenge(challenge.getChallengeId(), userId, challenge.getStatus());
		this.challRedis.writeReceiverChallenge(challenge.getChallengeId(), rivalId, challenge.getStatus());
		
		return challenge;
	}
	
	public Challenge createChallenge(Challenge challenge) {

		this.challRedis.updateChallenge(challenge.toString(), challenge.getChallengeId());
		this.challRedis.writeSenderChallenge(challenge.getChallengeId(), challenge.getUserId(), challenge.getStatus());
		this.challRedis.writeReceiverChallenge(challenge.getChallengeId(), challenge.getRivalId(), challenge.getStatus());
		
		return challenge;
	}
	
	/**
	 * 
	 * @param challengeId
	 * @return
	 */
	public Challenge getChallenge(String challengeId) {
		Challenge challenge = this.challRedis.getChallengeById(challengeId);
		
		/*CHECK CHALLENGE VALID*/
		String matchId = challenge.getMatchId();
		if(matchId == null) {
			return null;
		}
		MatchProcess matchProcess = new MatchProcess();
		MatchBaseInfo base = matchProcess.getMatchBaseInfo(matchId);
		if(base == null) {
			return null;
		}
		
		return challenge;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Challenge> getUnSeenRecChallList(String userId) {
		List<Challenge> challList = new ArrayList<Challenge>();
		
		challList = this.challRedis.getReceiverChallenge(userId, Challenge.ChallengeStatus.UNSEEN);
		
		Collections.sort(challList, new Comparator<Challenge>() {

			public int compare(Challenge first, Challenge second) {
				long val1 = first.getCreatedDate().getTime();
				long val2 = second.getCreatedDate().getTime();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		return challList;
	}
	
	public static class ChallFull extends Challenge {
		private UserBaseInfo user;
		private MatchBaseInfo match;
		private String topicName;
		
		public ChallFull() {
			this.setUser(new UserBaseInfo());
		}
		
		public ChallFull(Challenge challenge) {
			super(challenge);
		}
		
		public ChallFull(Challenge challenge, UserBaseInfo user, MatchBaseInfo match, String topicName) {
			super(challenge);
			this.user = user;
			this.setTopicName(topicName);
		}

		/**
		 * @return the user
		 */
		public UserBaseInfo getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(UserBaseInfo user) {
			this.user = user;
		}
		
		@Override
		public String toString() {
			String result = "";
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(this);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return result;
		}

		/**
		 * @return the topicName
		 */
		public String getTopicName() {
			return topicName;
		}

		/**
		 * @param topicName the topicName to set
		 */
		public void setTopicName(String topicName) {
			this.topicName = topicName;
		}

		/**
		 * @return the match
		 */
		public MatchBaseInfo getMatch() {
			return match;
		}

		/**
		 * @param match the match to set
		 */
		public void setMatch(MatchBaseInfo match) {
			this.match = match;
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<ChallFull> getFullUnSeenRecChallList(String receiv) {
		List<ChallFull> challList = new ArrayList<ChallFull>();
		
		List<Challenge> list = this.challRedis.getReceiverChallenge(receiv, Challenge.ChallengeStatus.UNSEEN);
		//logger.info(list);
		UserServiceProcess userProcess = new UserServiceProcess();
		MatchProcess matchProcess = new MatchProcess();
		
		TopicRedis topicRedis = new TopicRedis();
		
		for(Challenge chall : list) {
			String userId = chall.getUserId();
			UserBaseInfo user = userProcess.getUser(userId);
			//logger.info(user);
			Topic topic = topicRedis.getTopicById(chall.getTopicId());
			
			MatchBaseInfo match = matchProcess.getMatchBaseInfo(chall.getMatchId());
			
			if(user != null && topic != null) {
				ChallFull challenge = new ChallFull(chall, user, match, topic.getTitle());
				challList.add(challenge);
			} else {
				this.challRedis.removeChallenge(chall.getChallengeId());
			}
		}
		//logger.info(challList);
		
		Collections.sort(challList, new Comparator<ChallFull>() {

			public int compare(ChallFull first, ChallFull second) {
				long val1 = first.getCreatedDate().getTime();
				long val2 = second.getCreatedDate().getTime();
				
				if(val1 < val2) 
					return 1;
				else if ( val1 == val2) 
					return 0;
				else 
					return -1;
			}
			
		});
		
		return challList;
	}
	
	
	public List<Challenge> getRecChallList(String userId, String status) {
		List<Challenge> challList = new ArrayList<Challenge>();
		
		challList = this.challRedis.getReceiverChallenge(userId, status);
		
		return challList;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Challenge> getUnSeenSenList(String userId) {
		List<Challenge> challList = new ArrayList<Challenge>();
		
		challList = this.challRedis.getSenderChallenge(userId, Challenge.ChallengeStatus.UNSEEN);
		
		return challList;
	}
	
	public List<Challenge> getSenChallList(String userId, String status) {
		List<Challenge> challList = new ArrayList<Challenge>();
		
		challList = this.challRedis.getSenderChallenge(userId, status);
		
		return challList;
	}
	
	/**
	 * 
	 * @param challengeId
	 * @param oldStatus
	 * @param newStatus
	 * @return
	 */
	public Challenge updateStatus(String challengeId, String oldStatus, String newStatus) {
		Challenge challenge = null;
		
		this.challRedis.updateChallengeStatus(challengeId, oldStatus, newStatus);
		challenge = this.challRedis.getChallengeById(challengeId);
		if(challenge == null) {
			return null;
		}
		
		this.challRedis.updateSenderChallenge(challengeId, challenge.getUserId(), oldStatus, newStatus);
		this.challRedis.updateReceiverChallenge(challengeId, challenge.getRivalId(), oldStatus, newStatus);
		
		return challenge;
	}
	
	public Challenge updateStatus(String challengeId) {
		return this.updateStatus(challengeId, Challenge.ChallengeStatus.SEEN, Challenge.ChallengeStatus.UNSEEN);
	}
	
	/**
	 * 
	 * @param challengeId
	 */
	public Challenge removeChallenge(String challengeId) {
		Challenge chall = this.challRedis.getChallengeById(challengeId);
		if(chall == null)
			return chall;
		this.challRedis.removeChallenge(challengeId);
		this.challRedis.deleteSenderChallenge(challengeId, chall.getUserId(), chall.getStatus());
		this.challRedis.deleteReceiverChallenge(challengeId, chall.getRivalId(), chall.getStatus());
		
		return chall;
		
	}

}
