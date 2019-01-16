package com.duan.fantasies.distributedlock.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/1/14.
 * https://github.com/yujiasun/Distributed-Kit
 *
 * @author DuanJiaNing
 */
public interface DistributedReentrantLock {

    boolean tryLock(long timeout, TimeUnit unit);

    boolean tryLock();

    void unlock();

}
