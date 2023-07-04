package com.njupt.bean;

import com.njupt.springframework.context.event.base.ApplicationEvent;
import com.njupt.springframework.context.event.base.ApplicationListener;

import java.util.Date;

public class MyApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationEventListener收到：" + event.getSource() + "消息;时间：" + new Date());
    }
}
