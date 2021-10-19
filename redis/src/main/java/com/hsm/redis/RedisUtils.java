package com.hsm.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.api.redisnode.RedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname redisUtils
 * @Description TODO
 * @Date 2021/6/12 17:01
 * @Created by huangsm
 */
public class RedisUtils {

    public static Jedis getConnetion() {
        return new Jedis("192.168.106.133", 6379);
    }

    public static JedisCluster getClusterConnetion() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8000));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8010));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8011));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8020));
        jedisClusterNode.add(new HostAndPort("192.168.148.130", 8021));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "redis-pw", config);
        return jedisCluster;
    }
}
