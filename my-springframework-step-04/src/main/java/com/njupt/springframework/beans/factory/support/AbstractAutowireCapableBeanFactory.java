package com.njupt.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

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

        applyPropertyValues(beanName, bean, beanDefinition);

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

        applyPropertyValues(beanName, bean, beanDefinition);

        registerSingleton(beanName, bean);

        return bean;
    }

    /**
     * bean属性填充
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {


        // 获得需要填充的所有propertyValue
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

            String name = propertyValue.getName();
            Object value = propertyValue.getValue();

            //todo 处理属性依赖问题

            // 如果是对象属性，那么将其初始化后(getBean)再填充进来
            if (value instanceof BeanReference) {
                // A 依赖 B，获取 B 的实例化
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());

            }
            // 否则为普通属性

            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    public void setFieldValue(Object bean, String name, Object value) {
        try {
            Class<?> aClass = bean.getClass();
            System.out.println(aClass);
            Field field = bean.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (NoSuchFieldException e) {
            throw new BeansException("bean has no such property name" + name, e);
        } catch (IllegalAccessException e) {
            throw new BeansException("illegal access to bean value set", e);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
