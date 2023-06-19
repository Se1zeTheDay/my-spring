package com.njupt.springframework.factory;

public interface BeanFactory {

    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);


}
