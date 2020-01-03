package com.yilanjiaju.sulan.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;

@Component
public class AppContext implements ApplicationContextAware {

    private static AppContext instance;

    private static ApplicationContext applicationContext;
    private static ThreadLocal<String> requestLogId = new ThreadLocal<>();

    @PostConstruct
    public void init(){
        instance = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    //获取applicationContext
    public static ApplicationContext get() {
        return AppContext.applicationContext;
    }

    public static String getProperty(String name){
        return get().getBean(Environment.class).getProperty(name);
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return get().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return get().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz){
        return get().getBean(name, clazz);
    }

    public static String getRequestLogId(){
        return requestLogId.get();
    }

    public static void setRequestLogId(String logId){
        requestLogId.remove();
        requestLogId.set(logId);
    }

    public static HttpServletRequest getCurrentRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    public static HttpServletResponse getCurrentResponse(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        return response;
    }

    public static Executor getTaskExecutor(){
        return get().getBean("taskExecutor", ThreadPoolTaskExecutor.class);
    }


}
