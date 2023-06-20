package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctr, Object[] args) {

        Class<?> beanClass = beanDefinition.getBeanClass();

        try {
            if (null != ctr) {
                return beanClass.getDeclaredConstructor(ctr.getParameterTypes()).newInstance(args);
            } else {
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
