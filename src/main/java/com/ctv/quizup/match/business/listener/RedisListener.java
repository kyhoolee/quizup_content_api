package com.ctv.quizup.match.business.listener;

import com.ctv.quizup.redis.RedisPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public abstract class RedisListener {
	
	public abstract void process(String message);
	
	
	public void subscribeChannel(String channel) throws java.lang.InterruptedException {
		Jedis client = RedisPool.getJedis();
        client.subscribe(new JedisPubSub() {
            public void onMessage(String channel, String message) {
                System.out.println( String.format("Received data: %s", message) );
                process(message);
            }

            public void onSubscribe(String channel, int subscribedChannels) {  }

            public void onUnsubscribe(String channel, int subscribedChannels) { }

            public void onPSubscribe(String pattern, int subscribedChannels) { }

            public void onPUnsubscribe(String pattern, int subscribedChannels) {  }

            public void onPMessage(String pattern, String channel, String message) { }
			
        }, channel);
	}
}
