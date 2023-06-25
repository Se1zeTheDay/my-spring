package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.factory.BeanFactory;

/**
 * 可为bean添加后处理器
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);



}
