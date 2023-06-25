package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.factory.base.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
}
