package com.njupt;


import cn.hutool.core.io.IoUtil;
import com.njupt.bean.MyBeanFactoryPostProcessor;
import com.njupt.bean.MyBeanPostProcessor;
import com.njupt.bean.UserDao;
import com.njupt.bean.UserService;
import com.njupt.springframework.beans.factory.base.*;
import com.njupt.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.njupt.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.njupt.springframework.beans.factory.support.XmlBeanDefinitionReader;
import com.njupt.springframework.context.ClassPathXmlApplicationContext;
import com.njupt.springframework.core.io.DefaultResourceLoader;
import com.njupt.springframework.core.io.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void withoutApplicationContext() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(defaultListableBeanFactory);

        UserService userService = defaultListableBeanFactory.getBean("userService", UserService.class);

        String s1 = userService.queryUserInfo();
        System.out.println("测试结果1:" + s1);
        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();

        myBeanPostProcessor.postProcessBeforeInitialization(userService, "userService");

        String s = userService.queryUserInfo();
        System.out.println("测试结果2:" + s);
    }
    @Test
    public void useApplicationContext() {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);

        String result = userService.queryUserInfo();

        System.out.println(result);
    }


}
