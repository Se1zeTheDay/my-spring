package com.njupt.bean;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.*;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * 修改beanDefinition
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition userService = beanFactory.getBeanDefinition("userService");

        PropertyValues propertyValues = userService.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为:字节跳动"));
    }
}
