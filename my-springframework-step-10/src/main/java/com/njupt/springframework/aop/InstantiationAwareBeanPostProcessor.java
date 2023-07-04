package com.njupt.springframework.aop;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanPostProcessor;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
