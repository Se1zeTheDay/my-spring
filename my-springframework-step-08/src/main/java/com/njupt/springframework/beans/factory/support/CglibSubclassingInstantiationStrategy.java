package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.InstantiationStrategy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctr, Object[] args) {
        Enhancer enhancer = new Enhancer();

        // 设置被代理类
        enhancer.setSuperclass(beanDefinition.getBeanClass());

        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        if (ctr == null) return enhancer.create();

        return enhancer.create(ctr.getParameterTypes(), args);
    }
}
