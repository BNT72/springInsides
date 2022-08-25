package com.example.springInsides.postProcess;

import com.example.springInsides.annotation.InitPrepare;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Random;

@Component
public class InjectRandomNumberAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .forEach(f -> {
                    InitPrepare annotation = f.getAnnotation(InitPrepare.class);
                    if (null != annotation) {
                        int min = annotation.min();
                        int max = annotation.max();
                        Random random = new Random();
                        f.setAccessible(true);
                        ReflectionUtils.setField(f, bean, min + random.nextInt(max - min));
                    }

                });
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
