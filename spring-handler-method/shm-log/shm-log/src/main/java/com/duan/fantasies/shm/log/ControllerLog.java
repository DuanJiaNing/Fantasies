package com.duan.fantasies.shm.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2018/11/7.
 *
 * @author DuanJiaNing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLog {

    String value() default "";

    String[] tags() default "";

    String[] data() default "";

}
