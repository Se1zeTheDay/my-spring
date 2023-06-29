package com.njupt;


import com.njupt.bean.UserService;
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
        UserService userService1 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService);
        System.out.println(userService1);
        System.out.println(userService.queryUserInfo());
        System.out.println("ApplicationContextAware："+userService.getApplicationContext());
        System.out.println("BeanFactoryAware："+userService.getBeanFactory());

    }


}
