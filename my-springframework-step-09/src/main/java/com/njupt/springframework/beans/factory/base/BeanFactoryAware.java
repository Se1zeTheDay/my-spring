package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.BeanFactory;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
