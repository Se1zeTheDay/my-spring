package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 在所有的beanDefinition注册完成之后，实例化bean对象之前，
     * 提供修改BeanDefinition对象的机制, 对所有bean进行修改，
     * 因此传递参数为beanFactory
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
