package com.njupt.springframework.context.event.base;

import org.springframework.context.event.ApplicationContextEvent;

public class EventObject {

    /**
     * 事件发生来源，例如ApplicationContext
     */
    private Object source;

    public EventObject(Object source) {
        if (source == null) {
            throw new IllegalArgumentException("null source");
        }
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
