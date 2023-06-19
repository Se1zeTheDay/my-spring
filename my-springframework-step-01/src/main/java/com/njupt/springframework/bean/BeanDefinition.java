package com.njupt.springframework.bean;

/**
 * 拆解bean对象，用于管理bean
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
