package com.duan.fantasies.shm.verify.sample;

import com.duan.fantasies.shm.verify.HandlerMethodVerifyFail;
import com.duan.fantasies.shm.verify.ServletInvocableHandlerMethodArgumentVerify;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/1/31.
 *
 * @author DuanJiaNing
 */
@Component
public class HandlerMethodVerifyFailImpl implements HandlerMethodVerifyFail {

    @Override
    public Object warpVerifyFailResult(ServletInvocableHandlerMethodArgumentVerify.HandleResult result) {
        return "verify fail: " + result.parameter.getParameterName();
    }

}
