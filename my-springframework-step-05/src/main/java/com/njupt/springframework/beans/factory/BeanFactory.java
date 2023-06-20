package com.njupt.springframework.beans.factory;

public interface BeanFactory {

    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);


}
