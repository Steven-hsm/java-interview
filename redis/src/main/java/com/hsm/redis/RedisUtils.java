package com.hsm.redis;

import redis.clients.jedis.Jedis;

/**
 * @Classname redisUtils
 * @Description TODO
 * @Date 2021/6/12 17:01
 * @Created by huangsm
 */
public class RedisUtils {

    public static Jedis getConnetion(){
        return new Jedis("192.168.106.133",6379);
    }
}
