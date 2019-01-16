package com.duan.fantasies.distributedlock.util;

import com.duan.fantasies.distributedlock.lock.DistributedReentrantLock;
import com.duan.fantasies.distributedlock.lock.RedisReentrantLock;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;

/**
 * Created on 2019/1/15.
 *
 * @author DuanJiaNing
 */
public class LockUtil {
    
    public static DistributedReentrantLock getDistributedReentrantLock(String lockId) {
        RedisConnectionFactory redisConnectionFactory = SpringUtil.getBean(RedisConnectionFactory.class);
        JedisConnection connection = (JedisConnection) redisConnectionFactory.getConnection();
        JedisPool jedisPool = getJedisPool(connection);
        if (jedisPool != null) {
            return new RedisReentrantLock(jedisPool, lockId);
        }

        return null;
    }

    private static JedisPool getJedisPool(JedisConnection connection) {
        Class<JedisConnection> connectionClass = JedisConnection.class;
        try {
            Field pool = connectionClass.getDeclaredField("pool");
            ReflectionUtils.makeAccessible(pool);
            return ((JedisPool) pool.get(connection));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
