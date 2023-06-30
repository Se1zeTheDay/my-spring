package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.ApplicationContextAware;
import com.njupt.springframework.beans.factory.base.BeanPostProcessor;
import com.njupt.springframework.context.ApplicationContext;


/**
 * 让继承ApplicationContextAware接口的类能够感知当前的ApplicationContext
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
