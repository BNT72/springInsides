package com.example.springInsides.сontextListener;

import com.example.springInsides.annotation.PostProxy;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private final ConfigurableListableBeanFactory factory;

    public PostProxyInvokerContextListener(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(name -> {
                    BeanDefinition beanDefinition = factory.getBeanDefinition(name);
                    String originalClassName = beanDefinition.getBeanClassName();
                    if (originalClassName != null) {
                        try {
                            Class<?> originalClass = Class.forName(originalClassName);
                            Method[] methods = originalClass.getMethods();
                            for (Method method : methods) {
                                if (method.isAnnotationPresent(PostProxy.class)) {
                                    Object bean = applicationContext.getBean(name);
                                    Method method1 = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                                    method1.invoke(bean);
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


    }
}

