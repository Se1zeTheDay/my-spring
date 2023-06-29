package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.BeanFactory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回bean实例
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回所有bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
