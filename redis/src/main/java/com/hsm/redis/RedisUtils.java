package com.hsm.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.api.redisnode.RedisCluster;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname redisUtils
 * @Description TODO
 * @Date 2021/6/12 17:01
 * @Created by huangsm
 */
public class RedisUtils {

    public static Jedis getConnetion() {
        Jedis jedis = new Jedis("devredis.local.yjzhixue.com", 6379);
        jedis.auth("kpkj@2021");
        return jedis;

    }

    public static void main(String[] args) {
        JedisPool jedisPool = RedisUtils.getJedisPool();
        JedisCluster clusterConnetion = RedisUtils.getClusterConnetion();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10000; i++) {
            final  int j = i;
            executorService.submit(() ->{
                //ShardedJedis jedis = null;
                try{
                    String key = String.format("hsm:%s", j);
                    //jedis = jedisPool.getResource();
                    //jedis = RedisUtils.getConnetion();
                    //jedis = clusterConnetion.get();
                    clusterConnetion.set(key, key);
                    System.out.println(key);
                }finally {
                    /*if(jedis != null){
                        jedis.close();
                    }*/
                }
            });
        }
    }
    public static JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        return new JedisPool(jedisPoolConfig, "devredis.local.yjzhixue.com", 6379, 2, "kpkj@2021");
    }

    /*public static ShardedJedisPool  getShardedJedisPool() {
        ShardedPipeline
        List<jedis> shards = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo("devredis.local.yjzhixue.com",6379);
        jedisShardInfo.setPassword("kpkj@2021");
        shards.add(jedisShardInfo);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);
        ShardedJedisPool  shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);

        return shardedJedisPool;
    }*/
    public static JedisSentinelPool  getShardedJedisPool() {
        List<jedis> shards = new ArrayList<>();
        JedisSentinelPool jedisShardInfo = new JedisSentinelPool("devredis.local.yjzhixue.com",6379);
        jedisShardInfo.setPassword("kpkj@2021");
        shards.add(jedisShardInfo);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);
        ShardedJedisPool  shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);

        return shardedJedisPool;
    }

    public static JedisCluster getClusterConnetion() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("devredis.local.yjzhixue.com", 6379));
       /* jedisClusterNode.add(new HostAndPort("192.168.148.130", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8010));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8011));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8020));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8021));*/
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "kpkj@2021", config);
        return jedisCluster;
    }

}
