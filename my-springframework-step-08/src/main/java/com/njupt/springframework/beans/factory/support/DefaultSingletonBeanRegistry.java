package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.DisposableBean;
import com.njupt.springframework.beans.factory.base.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {

        Set<String> disposableBeanNames = disposableBeans.keySet();
        Object[] array = disposableBeanNames.toArray();
        for (int i = 0; i < array.length; i++) {
            Object beanName = array[i];

            DisposableBean disposableBean = disposableBeans.remove(beanName);

            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }

        }



    }

}

