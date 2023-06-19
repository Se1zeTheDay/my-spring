package com.njupt.springframework.factory;

import com.njupt.springframework.exception.BeansException;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();


    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {

        Constructor<?> constructor2Use = null;

        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        if (args != null) {
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if (declaredConstructor.getParameterTypes().length == args.length) {
                    constructor2Use = declaredConstructor;
                    break;
                }
            }
        }
        return instantiationStrategy.instantiate(beanDefinition,beanName, constructor2Use, args);

    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {

        Object bean = null;

        try {
            bean = createBeanInstance(beanName, beanDefinition, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerSingleton(beanName, bean);

        return bean;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {

        Object bean = null;

        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerSingleton(beanName, bean);

        return bean;
    }
}
