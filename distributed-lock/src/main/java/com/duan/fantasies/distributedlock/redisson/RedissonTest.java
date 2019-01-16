package com.duan.fantasies.distributedlock.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * Created on 2019/1/16.
 *
 * @author DuanJiaNing
 */
public class RedissonTest {

    public void test() {
        RedissonClient redissonClient = Redisson.create();
        RLock rLock = redissonClient.getLock("test");
        rLock.lock();
        // ...
        rLock.unlock();
    }

}
