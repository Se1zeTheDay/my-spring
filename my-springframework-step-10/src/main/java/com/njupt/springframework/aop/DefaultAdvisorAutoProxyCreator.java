package com.njupt.springframework.aop;

import com.njupt.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.njupt.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanFactoryAware;
import com.njupt.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import com.njupt.springframework.beans.factory.BeanFactory;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        if (isInfrastructureClass(beanClass)) return null;

        // 获得所有Advisors
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();

            if (!classFilter.matches(beanClass)) continue;

            // 满足过滤条件
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor(((MethodInterceptor) advisor.getAdvice()));
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

            return new ProxyFactory(advisedSupport).getProxy();

        }
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws org.springframework.beans.BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
