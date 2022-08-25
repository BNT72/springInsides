package com.example.springInsides.postProcess;

import com.example.springInsides.annotation.TimeLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String,Class> beanMap=new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if(aClass.isAnnotationPresent(TimeLogger.class)){
            beanMap.put(beanName, aClass);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class aClass = beanMap.get(beanName);
        if(null!=aClass){
            return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Log time");
                    long before = System.nanoTime();
                    Object invoke = method.invoke(bean, args);
                    System.out.println(System.nanoTime()-before);
                    System.out.println("End");
                    return invoke;
                }
            });
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
