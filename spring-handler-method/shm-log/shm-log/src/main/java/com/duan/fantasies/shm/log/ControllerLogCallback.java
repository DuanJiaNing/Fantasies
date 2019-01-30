package com.duan.fantasies.shm.log;

import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * Created on 2019/1/30.
 *
 * @author DuanJiaNing
 * @see ControllerLog
 */
public interface ControllerLogCallback {

    /**
     * ControllerLog 注解没有指定内容，此时是默认注解
     *
     * @param result        controller 方法调用结果
     * @param handlerMethod controller 方法
     * @param args          controller 方法参数
     */
    void handleDefaultLog(Object result, ServletInvocableHandlerMethod handlerMethod, Object[] args);

    /**
     * 日志表达式解析成功
     *
     * @param content       解析结果
     * @param tags          日志标签
     * @param datas         controller 入参或返回值
     * @param result        controller 方法调用结果
     * @param handlerMethod controller 方法
     * @param args          controller 方法参数
     */
    void handleCustomLog(String content, String[] tags, Object[] datas, Object result,
                         ServletInvocableHandlerMethod handlerMethod, Object[] args);
}
