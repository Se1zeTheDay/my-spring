package com.njupt.springframework.context;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.ConfigurableListableBeanFactory;
import com.njupt.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        // 1. 初始化beanFactory
        DefaultListableBeanFactory beanFactory = createBeanFactory();

        // 2. 加载beanDefinition, 由于可以通过不同方式加载，因此还是做成了抽象方法
        loadBeanDefinitions(beanFactory);

        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
