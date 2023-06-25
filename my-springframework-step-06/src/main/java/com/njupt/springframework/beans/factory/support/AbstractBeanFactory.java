package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.factory.BeanFactory;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.BeanPostProcessor;
import com.njupt.springframework.beans.factory.base.ConfigurableBeanFactory;
import com.njupt.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * template method设计模式
     * 获得bean
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        return getBean(beanName, (Object) null);
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

    public <T> T getBean(String name, Class<T> requiredType){
        return (T) getBean(name);
    }

    /**
     * 通过beanDefinition创建bean
     * @param beanName
     * @param beanDefinition
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);


    /**
     * 获取beanDefinition
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
}
