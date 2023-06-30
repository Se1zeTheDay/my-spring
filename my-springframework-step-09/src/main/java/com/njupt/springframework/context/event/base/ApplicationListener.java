package com.njupt.springframework.context.event.base;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * 事件发生时
     * @param event
     */
    void onApplicationEvent(E event);
}
