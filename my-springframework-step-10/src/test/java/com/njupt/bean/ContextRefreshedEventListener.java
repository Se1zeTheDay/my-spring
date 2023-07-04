package com.njupt.bean;

import com.njupt.springframework.context.event.base.ApplicationListener;
import com.njupt.springframework.context.event.support.ContextRefreshedEvent;

import java.util.Date;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedEventListener收到：" + event.getSource() + "消息;时间：" + new Date());
    }
}
