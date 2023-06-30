package com.njupt.springframework.context;


import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanFactoryPostProcessor;
import com.njupt.springframework.beans.factory.base.BeanPostProcessor;
import com.njupt.springframework.beans.factory.base.ConfigurableListableBeanFactory;
import com.njupt.springframework.beans.factory.support.ApplicationContextAwareProcessor;
import com.njupt.springframework.context.event.base.ApplicationEvent;
import com.njupt.springframework.context.event.base.ApplicationEventMulticaster;
import com.njupt.springframework.context.event.base.ApplicationEventPublisher;
import com.njupt.springframework.context.event.base.ApplicationListener;
import com.njupt.springframework.context.event.support.ContextClosedEvent;
import com.njupt.springframework.context.event.support.ContextRefreshedEvent;
import com.njupt.springframework.context.event.support.SimpleApplicationEventMulticaster;
import com.njupt.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() {

        // 1. 创建beanFactory, 加载beanDefinition
        refreshBeanFactory();

        // 通过getFactory()方法获得beanFactory实例
        // 这就与beanFactory联系起来
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 让所有继承ApplicationContextAware的bean对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 2. BeanDefinition已经加载完毕，此时需要执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 之前bean的实例化通过getBean方法执行
        // 3. BeanPostProcessor 需要提前于其它bean对象实例化之前进行注册操作

        // 猜想：为什么beanFactoryPostProcessor直接触发，而beanPostProcessor需要注册并记录到beanFactory中，
        // 因为beanFactoryPostProcessor对于所有的beanDefinition进行,并且beanDefinition的注册是统一完成的，
        // 而bean实例化的时间有差异，因此选择先记录下所有的BeanPostProcessor，等后面实例化时再对bean进行操作
        registerBeanPostProcessors(beanFactory);

        // 4. 实例化单例bean对象
        beanFactory.preInstantiateSingletons();

        // 5. 初始化事件广播器
        initApplicationEventMulticaster();

        // 6. 注册所有监听器到事件广播器中
        registerListeners();

        // 7. 发布容器刷新完成事件
        finishRefresh();

        registerShutDownHook();

    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();

        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void registerShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {

        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            // 将beanPostProcessor添加至beanFactory中
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    // 触发所有的beanFactoryPostProcessor
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 获取所有BeanFactoryPostProcessor的bean，此时只有beanDefinition，猜想：是getBeansOfType时进行了对应bean的实例化？
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
