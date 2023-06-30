package com.njupt.springframework.beans.factory.base;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.context.ApplicationContext;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
