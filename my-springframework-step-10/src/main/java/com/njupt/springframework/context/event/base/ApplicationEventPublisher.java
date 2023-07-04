package com.njupt.springframework.context.event.base;

/**
 * 若一个应用想要拥有事件发布功能，需要继承该接口
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     * @param event
     */
    void publishEvent(ApplicationEvent event);

}
