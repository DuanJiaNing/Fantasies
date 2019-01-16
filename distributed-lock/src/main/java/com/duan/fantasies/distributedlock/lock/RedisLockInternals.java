package com.duan.fantasies.distributedlock.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created on 2019/1/14.
 * https://github.com/yujiasun/Distributed-Kit
 *
 * @author DuanJiaNing
 */
public class RedisLockInternals {

    private final JedisPool jedisPool;

    // 重试等待时间
    private int retryAwait = 300;

    private int lockTimeout = 2000;

    RedisLockInternals(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    RedisLockInternals(JedisPool jedisPool, int retryAwait, int lockTimeout) {
        this.jedisPool = jedisPool;
        this.retryAwait = retryAwait;
        this.lockTimeout = lockTimeout;
    }

    RedisLockInternals(JedisPool jedisPool, int lockTimeout) {
        this.jedisPool = jedisPool;
        this.lockTimeout = lockTimeout;
    }

    String tryRedisLock(String lockId, long time, TimeUnit unit) {
        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;

        String lockValue;
        while (true) {
            lockValue = createRedisKey(lockId);
            if (lockValue != null) {
                break;
            }
            if (System.currentTimeMillis() - startMillis - retryAwait > millisToWait) {
                break;
            }
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryAwait));
        }
        return lockValue;
    }

    String tryRedisLock(String lockId) {

        String lockValue;
        while (true) {
            lockValue = createRedisKey(lockId);
            if (lockValue != null) {
                break;
            }

            // 等待时间，减少 redis 压力
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryAwait));
        }
        return lockValue;
    }

    private String createRedisKey(String lockId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = lockId + randomId(1);
            String luaScript = ""
                    + "\nlocal r = tonumber(redis.call('SETNX', KEYS[1],ARGV[1]));"
                    + "\nredis.call('PEXPIRE',KEYS[1],ARGV[2]);" // 锁的失效设置。避免单点故障造成死锁
                    + "\nreturn r";
            List<String> keys = new ArrayList<String>();
            keys.add(lockId);

            List<String> args = new ArrayList<String>();
            args.add(value);
            args.add(lockTimeout + "");

            Long ret = (Long) jedis.eval(luaScript, keys, args);
            if (new Long(1).equals(ret)) {
                return value;
            }
        } finally {
            if (jedis != null)
                jedis.close();
        }

        return null;
    }

    void unlockRedisLock(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String luaScript = ""
                    + "\nlocal v = redis.call('GET', KEYS[1]);"
                    + "\nlocal r= 0;"
                    + "\nif v == ARGV[1] then"
                    + "\nr =redis.call('DEL',KEYS[1]);"
                    + "\nend"
                    + "\nreturn r";
            List<String> keys = new ArrayList<String>();
            keys.add(key);

            List<String> args = new ArrayList<String>();
            args.add(value);

            Object r = jedis.eval(luaScript, keys, args);
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    private String randomId(int size) {
        char[] cs = new char[size];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = digits[ThreadLocalRandom.current().nextInt(digits.length)];
        }
        return new String(cs);
    }

}
