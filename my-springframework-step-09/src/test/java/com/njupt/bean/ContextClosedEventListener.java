package com.njupt.bean;

import com.njupt.springframework.context.event.base.ApplicationListener;
import com.njupt.springframework.context.event.support.ContextClosedEvent;

import java.util.Date;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("ContextClosedEventListener收到：" + event.getSource() + "消息;时间：" + new Date());
    }
}
