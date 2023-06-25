package com.njupt;


import com.njupt.bean.MyBeanFactoryPostProcessor;
import com.njupt.bean.MyBeanPostProcessor;
import com.njupt.bean.UserService;
import com.njupt.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.njupt.springframework.beans.factory.support.XmlBeanDefinitionReader;
import com.njupt.springframework.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test_xml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService.queryUserInfo());

    }


}
