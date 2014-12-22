package com.ctv.quizup.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// CHOOSE REDIS PORT AND INDEX HERE !!!
	//////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private static JedisPool _instance = null;
	public static final int DATABASE_INDEX = 4;
	

	public static final String REDIS_REMOTE_ADDRESS = "5play.mobi";
	public static final String REDIS_LOCAL_ADDRESS = "localhost";
	
	public static final int REDIS_SERVER_PORT = 9995;
	public static final int REDIS_LOCAL_PORT = 6379; 
	public static final int REDIS_REMOTE_PORT = 10103;
	// 5play.mobi , 10103


	private RedisPool() {
	}

	public static JedisPool getIntance() {

		if (_instance == null) {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(100);
			jedisPoolConfig.setMaxIdle(100);
			jedisPoolConfig.setMaxWaitMillis(10000);
			jedisPoolConfig.setMinIdle(10);
			jedisPoolConfig.setTimeBetweenEvictionRunsMillis(5000);
			jedisPoolConfig.setMinEvictableIdleTimeMillis(1000);
			_instance = new JedisPool(jedisPoolConfig, REDIS_REMOTE_ADDRESS, REDIS_REMOTE_PORT, 10000);
		}
		return _instance;
	}
	
	public static Jedis getJedis() {
		Jedis jedis = RedisPool.getIntance().getResource();
		jedis.select(DATABASE_INDEX);
		return jedis;
	}
}
