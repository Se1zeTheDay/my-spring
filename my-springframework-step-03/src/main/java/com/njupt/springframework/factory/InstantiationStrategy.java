package com.njupt.springframework.factory;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    /**
     * Constructor 是 java.lang.reflect 包下的 Constructor 类
     * 包含了一些必要的类信息，有这个参数的目的就是为了拿到符合入参信息相对应的构造函数。
     * @param beanDefinition
     * @param beanName
     * @param ctr
     * @param args
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctr, Object[] args);

}
