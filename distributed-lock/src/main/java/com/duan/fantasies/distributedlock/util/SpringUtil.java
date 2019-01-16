package com.duan.fantasies.distributedlock.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Created on 2018/9/29.
 *
 * @author DuanJiaNing
 */
public class SpringUtil {
    private static ApplicationContext applicationContext = null;

    public SpringUtil() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // ApplicationContextAware#setApplicationContext 中进行赋值
    public static void setApplicationContext(ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    public static String getProperties(String key) {
        Environment environment = getBean(Environment.class);
        return environment.getProperty(key);
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
