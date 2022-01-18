package com.hsm.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
    public void jedisPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        /*for (int i = 0; i < 100; i++) {
            final  int j = i;
            executorService.submit(() ->{
                Jedis jedis = null;
                try{
                    String key = String.format("hsm:%s", j);
                    jedis = RedisUtils.getJedisPool().getResource();
                    jedis.set(key, key);
                    System.out.println(key);
                }finally {
                    if(jedis != null){
                        jedis.close();
                    }
                }
            });
        }*/
        final  int j = 1;
        new Thread(() -> {
            Jedis jedis = null;
            try{
                String key = String.format("hsm:%s", j);
                System.out.println(key);
                /*RedisUtils.getJedisPool();
                jedis = RedisUtils.getJedisPool().getResource();*/
                jedis = RedisUtils.getConnetion();
                jedis.set(key, key);
                System.out.println(key);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                if(jedis != null){
                    jedis.close();
                }
            }
        }).start();
        /*executorService.submit(() ->{
            Jedis jedis = null;
            try{
                String key = String.format("hsm:%s", j);
                jedis = RedisUtils.getJedisPool().getResource();
                jedis.set(key, key);
                System.out.println(key);
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }
        });*/

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


    @Test
    public void clusterSet() {
        JedisCluster cluster = RedisUtils.getClusterConnetion();
        cluster.set("abc", "2131231");

    }
}