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
        // 创建bean工厂
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService", "123");

        userService.queryUserInfo();

        //UserService userService1 = (UserService) beanFactory.getBean("userService1", "233");

        System.out.println(userService);
        //System.out.println(userService1);


    }
}
