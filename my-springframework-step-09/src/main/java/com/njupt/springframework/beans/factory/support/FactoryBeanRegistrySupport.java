package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * 由factory bean创建的singleton objects的cache
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {

        // 如果是singleton，则需要存到cache中
        if (factoryBean.isSingleton()) {

            // double-check
            Object object = factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryBeanObjectCache.put(beanName, object);
            }
            return object;
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }

    }

    protected Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }




}
