package com.duan.fantasies.shm.core;

import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * Created on 2019/1/31.
 *
 * @author DuanJiaNing
 */
public interface HandlerMethodVerify {

    /**
     * controller 层方法调用前进行校验
     *
     * @param handlerMethod HandlerMethod
     * @param args          controller 方法参数
     * @return 返回值不为 null 时，controller 层将直接返回，而不执行逻辑，或是 postProcessorBeforeInvoke 和
     * postProcessorAfterInvoke 方法
     */
    default Object postVerifyBeforeInvoke(ServletInvocableHandlerMethod handlerMethod, Object... args) {
        return null;
    }

}
