package com.njupt.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 负责bean的实例化及自动注入
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

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

            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

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

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);



        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);

            if (null == current) return result;

            result = current;

        }
        return result;

    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);

            if (null == current) return result;

            result = current;

        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


}
