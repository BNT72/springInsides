package com.example.springInsides.postProcess;

import com.example.springInsides.annotation.DeprecatedClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DeprecationHandlerBeanFactoryPoserProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Arrays.stream(configurableListableBeanFactory.getBeanDefinitionNames())
                .forEach(str -> {
                    BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(str);
                    String beanClassName = beanDefinition.getBeanClassName();
                    try {
                        if (beanClassName != null) {
                            Class<?> aClass = Class.forName(beanClassName);
                            DeprecatedClass annotation = aClass.getAnnotation(DeprecatedClass.class);
                            if (annotation != null) {
                                beanDefinition.setBeanClassName(annotation.newImpl().getName());
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
