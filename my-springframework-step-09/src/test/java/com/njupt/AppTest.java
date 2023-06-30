package com.njupt;


import com.njupt.bean.CustomEvent;
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

        applicationContext.publishEvent(new CustomEvent(applicationContext, 9147892398L, "success"));


    }


}
