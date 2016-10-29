package com.bob.springboot.simple.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created by Bob Jiang on 2016/10/28.
 */
public class ApplicationContextUtil {

    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private static ApplicationContext getContext() {
        if (context == null)
            throw new RuntimeException("can't get ApplicationContext");
        return context;
    }

    public static <T> T getBean(Class<T> clazz){
        return getContext().getBean(clazz);
    }

    public static Object getBean(String name){
        return getContext().getBean(name);
    }
}
