package com.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		Jedis js = new Jedis("192.168.1.27", 6379);
        System.out.println(js.ping());
	}
	
}
