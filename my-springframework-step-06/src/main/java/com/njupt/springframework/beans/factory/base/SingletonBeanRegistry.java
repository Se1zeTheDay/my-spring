package com.njupt.springframework.beans.factory.base;

/**
 * 单例bean的注册
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);

}
