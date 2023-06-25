package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.factory.base.BeanDefinitionReader;
import com.njupt.springframework.beans.factory.base.BeanDefinitionRegistry;
import com.njupt.springframework.core.io.DefaultResourceLoader;
import com.njupt.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader;
    }

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this(beanDefinitionRegistry, new DefaultResourceLoader());
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }
}
