package com.njupt.springframework.context.event.base;

import com.njupt.springframework.context.ApplicationContext;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return ((ApplicationContext) getSource());
    }
}
