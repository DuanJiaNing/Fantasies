package com.duan.fantasies.distributedlock.config;

import com.duan.fantasies.distributedlock.lock.DistributedReentrantLock;
import com.duan.fantasies.distributedlock.util.LockUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created on 2019/1/14.
 *
 * @author DuanJiaNing
 */
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        DistributedReentrantLock lock = LockUtil.getDistributedReentrantLock("activityMessageManager#refresh");
        if (lock != null && lock.tryLock()) {
            // do something
            lock.unlock();
        }
    }
}
