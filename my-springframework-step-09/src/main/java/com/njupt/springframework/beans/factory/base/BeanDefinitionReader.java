package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.core.io.Resource;
import com.njupt.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getBeanDefinitionRegistry();

    ResourceLoader getResourceLoader();

    // 支持三种方式加载beanDefinition
    void loadBeanDefinitions(Resource resource) throws BeansException;
    void loadBeanDefinitions(Resource... resources) throws BeansException;
    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}
