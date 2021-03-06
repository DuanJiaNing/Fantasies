package com.duan.fantasies.shm.verify.annoation.method;

import com.duan.fantasies.shm.verify.enums.VerifyValueRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2018/9/14.
 *
 * @author DuanJiaNing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParamValueVerify {

    /**
     * 需要进行校验的参数名
     */
    String param();

    /**
     * 校验规则
     */
    VerifyValueRule rule() default VerifyValueRule.EQUAL;

    /**
     * 校验值
     */
    String value();

}
