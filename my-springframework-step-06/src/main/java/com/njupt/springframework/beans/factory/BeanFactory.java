package com.njupt.springframework.beans.factory;

import com.njupt.springframework.beans.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);

    <T> T getBean(String name, Class<T> requiredType);
}
