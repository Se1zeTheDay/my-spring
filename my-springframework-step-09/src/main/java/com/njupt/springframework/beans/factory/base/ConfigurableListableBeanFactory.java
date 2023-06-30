package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons();

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
