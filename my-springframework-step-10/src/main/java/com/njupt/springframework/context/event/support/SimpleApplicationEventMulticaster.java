package com.njupt.springframework.context.event.support;

import com.njupt.springframework.context.event.base.ApplicationEvent;
import com.njupt.springframework.context.event.base.ApplicationListener;

import java.util.Collection;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {


    @Override
    public void multicastEvent(ApplicationEvent event) {
        Collection<ApplicationListener<?>> applicationListeners = getApplicationListeners(event);

        for (ApplicationListener applicationListener : applicationListeners) {
            applicationListener.onApplicationEvent(event);
        }

    }
}
