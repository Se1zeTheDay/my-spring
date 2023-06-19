package com.njupt.springframework.factory;

import com.njupt.springframework.exception.BeansException;

public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {

        Object bean = null;

        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerSingleton(beanName, bean);

        return bean;
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }
}
