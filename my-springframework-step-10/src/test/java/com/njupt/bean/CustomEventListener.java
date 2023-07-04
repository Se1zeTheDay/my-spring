package com.njupt.bean;

import com.njupt.springframework.context.event.base.ApplicationListener;

import java.util.Date;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("CustomEventListener收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("CustomEventListener消息：" + event.getId() + ":" + event.getMessage());
    }

}
