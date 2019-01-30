package com.duan.fantasies.shm.sample;

import com.duan.fantasies.shm.log.ControllerLogCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * Created on 2019/1/30.
 *
 * @author DuanJiaNing
 */
@Component
public class ControllerLogCallbackImpl implements ControllerLogCallback {

    @Override
    public void handleDefaultLog(Object result, ServletInvocableHandlerMethod handlerMethod, Object[] args) {
//        log.error(handlerMethod.toString());
    }

    @Override
    public void handleCustomLog(String content, String[] tags, Object[] datas, Object result, ServletInvocableHandlerMethod handlerMethod, Object[] args) {
//        log.error(handlerMethod.toString() + ":" + content);
    }
}
