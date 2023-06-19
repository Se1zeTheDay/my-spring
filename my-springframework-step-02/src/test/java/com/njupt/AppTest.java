package com.njupt;


import com.njupt.bean.UserService;
import com.njupt.springframework.factory.BeanDefinition;
import com.njupt.springframework.factory.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test_beanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);

        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");

        userService.queryUserInfo();

        UserService userService1 = (UserService) beanFactory.getBean("userService");

        System.out.println(userService);
        System.out.println(userService1);


    }
}
