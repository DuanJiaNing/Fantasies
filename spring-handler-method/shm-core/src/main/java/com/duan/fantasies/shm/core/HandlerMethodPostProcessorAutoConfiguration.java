package com.duan.fantasies.shm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.util.List;

/**
 * Created on 2018/11/8.
 *
 * @author DuanJiaNing
 */
@Configuration
@ConditionalOnClass(HandlerMethodPostProcessor.class)
public class HandlerMethodPostProcessorAutoConfiguration implements WebMvcRegistrations {

    @Autowired
    private HandlerMethodPostProcessorComposite handlerMethodPostProcessors;

    @Bean
    public HandlerMethodPostProcessorComposite handlerMethodPostProcessors(List<HandlerMethodPostProcessor> processors) {
        return new HandlerMethodPostProcessorComposite(processors);
    }

    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter() {
            @Override
            protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
                return new HandlerMethodWrapper(handlerMethod, handlerMethodPostProcessors);
            }
        };
    }

}
