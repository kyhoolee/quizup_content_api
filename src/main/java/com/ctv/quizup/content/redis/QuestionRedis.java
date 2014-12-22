package com.ctv.quizup.content.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.ctv.quizup.content.model.Question;
import com.ctv.quizup.redis.RedisPool;
import com.ctv.quizup.util.LoggerUtil;

public class QuestionRedis {
	public static Logger logger = LoggerUtil.getDailyLogger("QuestionRedis" + "_error");
	
	
	
	public static final String QUESTION_HASHES = "quizup_content:question_hashes"; 
	
	public static final String TOPIC_QUESTION_LEVEL_SCORE_SORTED = "quizup_content:question_level_score_sorted:";// + topic + level
	public static final String TOPIC_QUESTION_LEVEL_SCORE_HASHES = "quizup_content:question_level_score_hashes:";//
	


	public Jedis redis;
	
//	public String getLevelScoreQuestionHashes(String topicId, int level, String scoreType) {
//		return TOPIC_QUESTION_LEVEL_SCORE_HASHES + "" + topicId + ":" + level;
//	}
	public String getLevelScoreQuestionSorted(String topicId, int level, String scoreType) {
		return TOPIC_QUESTION_LEVEL_SCORE_SORTED + "" + topicId + ":" + level;
	}
	public String getQuestionKey() {
		return QUESTION_HASHES;
	}
	
	public void removeQuestionFromRedis(String questionJSON, String questionId) {

		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hdel(this.getQuestionKey(), questionId, questionJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}

	public void writeQuestionToRedis(String questionJSON, String questionId) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.hset(this.getQuestionKey(), questionId, questionJSON);
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public void updateQuestionScore(String questionId, String topicId, int level, double score, String scoreType) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.zadd(this.getLevelScoreQuestionSorted(topicId, level, scoreType), score, questionId);
			jedis.hset(this.getLevelScoreQuestionSorted(topicId, level, scoreType), questionId, score + "");
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
	}
	
	public long getNumQuestionByTopic(String topicId, String scoreType) {
		long number = 0;
		Jedis jedis = RedisPool.getJedis();
		try {
			number = jedis.zcard(this.getLevelScoreQuestionSorted(topicId, 0, scoreType));
			return number;
			
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		return number;
	}
	
	public List<Question> getRandomQuestionByScore(String topicId, int level, String scoreType, int number) {
		
		List<Question> quesList = new ArrayList<Question>();
		
		Jedis jedis = RedisPool.getJedis();
		try {
			long count = jedis.zcard(this.getLevelScoreQuestionSorted(topicId, level, scoreType));
			Random random = new Random();
			long start = random.nextInt((int)count - number + 1);
			long end = start + number - 1;
			
					
			Set<String> quesIdList = jedis.zrevrange(this.getLevelScoreQuestionSorted(topicId, level, scoreType), start, end);
			
			for(String quesId : quesIdList) {
				quesList.add(this.getQuestionById(quesId));
			}
			
			return quesList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//System.out.println(e.toString());
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return quesList;
	}
	
	public static void main(String[] args) {
//		int top = 10;
//		int i = 9;
//		Random random = new Random();
//		long start = random.nextInt((int)top - i) + i;
//		System.out.println(start);
		int number = 5;
		long top = 100;
		ArrayList<Integer> list= new ArrayList<Integer>();
		for(int i = number - 1 ; i >= 0 ; i --) {
			
		
			Random random = new Random();
			//random.nextInt(n)
			int start = random.nextInt((int)top - i) + i;
			System.out.println("[" + top + " , " + i + "] : " + start);
			
					
			//Set<String> quesIdList = jedis.zrevrange(this.getLevelScoreQuestionSorted(topicId, level, scoreType), start, start);
			list.add(start);
			
			
			top = start;
		}
		
		System.out.println(list);
	}
	
	public Set<Integer> getPermutation(int top, int number) {
		Set<Integer> result = new HashSet<Integer>();
		
		Random random = new Random();
		
		for(int i = 0 ; i < number ; i ++) {
			while(true) {
				int start = random.nextInt(top);
				if(!result.contains(start)) {
					result.add(start);
					break;
				}
			}
		}
		return result;
	}
	
	public List<Question> getRandQuestPackage(String topicId, int level, String scoreType, int number) {
		
		List<Question> quesList = new ArrayList<Question>();
		
		Jedis jedis = RedisPool.getJedis();
		try {
			long count = jedis.zcard(this.getLevelScoreQuestionSorted(topicId, level, scoreType));
			long top = count;
			Set<Integer> idSet = this.getPermutation((int)top, number);
			for(int start : idSet) {
				Set<String> quesIdList = jedis.zrevrange(
						this.getLevelScoreQuestionSorted(topicId, level, scoreType), start, start);
				
				for(String quesId : quesIdList) {
					quesList.add(this.getQuestionById(quesId));
				}
				
				top = start;
			}
			return quesList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//System.out.println(e.toString());
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return quesList;
	}
	
	public List<Question> getRandomQuestionPackage(String topicId, int level, String scoreType, int number) {
		
		List<Question> quesList = new ArrayList<Question>();
		
		Jedis jedis = RedisPool.getJedis();
		try {
			long count = jedis.zcard(this.getLevelScoreQuestionSorted(topicId, level, scoreType));
			long top = count;
			for(int i = number - 1 ; i >= 0 ; i --) {
				
			
				Random random = new Random();
				//random.nextInt(n)
				long start = random.nextInt((int)top - i) + i;
				
						
				Set<String> quesIdList = jedis.zrevrange(this.getLevelScoreQuestionSorted(topicId, level, scoreType), start, start);
				
				for(String quesId : quesIdList) {
					quesList.add(this.getQuestionById(quesId));
				}
				
				top = start;
			}
			return quesList;
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
			//System.out.println(e.toString());
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		return quesList;
	}
	
	public Question getQuestionById(String questionId) {
		Question question = null;
		
		/*READ TOPIC-QUESTION-SET FROM REDIS*/
		String json = "";
		Jedis jedis = RedisPool.getJedis();
		try {
			// Add to sorted-set
			json = jedis.hget(this.getQuestionKey(), questionId);
		} catch (JedisConnectionException ex) {
			RedisPool.getIntance().returnBrokenResource(jedis);
			
		} catch (Exception e) {
			logger.error(e);
		} finally {
			RedisPool.getIntance().returnResource(jedis);
			
		}
		
		
		/*PARSE QUESTION FROM JSON TO JAVA-OBJECT*/
		ObjectMapper mapper = new ObjectMapper(); 	
			try {
				question = mapper.readValue(json, Question.class);
				return question;
			} catch (Exception e) {
				logger.error(e);
				//System.out.println(e.toString());
				return null;
			}
		
		
	}


}
