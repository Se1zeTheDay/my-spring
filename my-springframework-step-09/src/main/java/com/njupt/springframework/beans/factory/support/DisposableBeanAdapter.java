package com.njupt.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.DisposableBean;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {


    private final Object bean;

    private final String beanName;

    private final String destroyMethodName;


    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {

        // 第一种，实现DisposableBean接口
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 第二种，配置了destroyMethod, 判断避免二次销毁
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named" + destroyMethodName + "on bean with name '" + beanName+ "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
