package com.njupt.springframework.context.event.support;

import com.njupt.springframework.context.event.base.ApplicationEvent;
import com.njupt.springframework.context.event.base.ApplicationEventMulticaster;
import com.njupt.springframework.context.event.base.ApplicationListener;
import com.njupt.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * 若事件发生，需要寻找到对事件感兴趣的listeners
     */
    protected Collection<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener<?>> allListeners = new LinkedList<>();

        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, event)) allListeners.add(applicationListener);
        }

        return allListeners;
    }

    /**
     * 判断是否感兴趣
     * @param applicationListener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {

        Class<? extends ApplicationListener> listenerClass  = applicationListener.getClass();

        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        // 获得该类实现的接口的泛型类型
        Type actualTypeArgument = null;
        for (Type type : targetClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        }

        assert actualTypeArgument != null;
        String className = actualTypeArgument.getTypeName();

        // 获得该applicationLister类实现接口的泛型类型的具体Class类
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 事件对应的具体Class类
        // 判断eventClassName是否是event.getClass()的父类或者相同类， 返回true说明感兴趣
        return eventClassName.isAssignableFrom(event.getClass());
    }


}
