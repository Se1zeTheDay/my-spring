package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.factory.BeanFactory;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * template method设计模式
     * 获得bean
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }

    /**
     * 通过beanDefinition创建bean
     * @param beanName
     * @param beanDefinition
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    /**
     * 获取beanDefinition
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
