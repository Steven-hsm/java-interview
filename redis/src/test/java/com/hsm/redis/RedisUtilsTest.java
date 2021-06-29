package com.hsm.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Classname redisUtilsTest
 * @Description TODO
 * @Date 2021/6/12 17:06
 * @Created by huangsm
 */
public class RedisUtilsTest {

    @Test
    public void getConnetion() {

    }

    @Test
    public void ping() {
        Jedis jedis = RedisUtils.getConnetion();
        String ping = jedis.ping();
        System.out.println(ping);
    }

    @Test
    public void lock() {
        Jedis jedis = RedisUtils.getConnetion();
        System.out.println(jedis.setnx("taskId", "taskId"));
        System.out.println(jedis.setnx("taskId", "taskId"));

    }


    @Test
    public void lock2() {
        Jedis jedis = RedisUtils.getConnetion();
        String ping = jedis.ping();
        System.out.println(ping);
    }


}