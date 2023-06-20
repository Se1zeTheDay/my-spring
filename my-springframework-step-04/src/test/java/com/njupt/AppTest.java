package com.njupt;


import com.njupt.bean.UserDao;
import com.njupt.bean.UserService;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.BeanReference;
import com.njupt.springframework.beans.factory.base.PropertyValue;
import com.njupt.springframework.beans.factory.base.PropertyValues;
import com.njupt.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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
        // UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // UserService 注册
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "2323"));
        propertyValues.addPropertyValue(new PropertyValue("password","234"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        beanDefinition.setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService", "23412");

        userService.queryUserInfo("10001");

        System.out.println(userService);

    }

    @Test
    public void testRf() {

        UserService userService = new UserService("test");

        try {
            Field field = UserService.class.getDeclaredField("id");
            field.setAccessible(true);
            try {
                field.set(userService, "234");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        String id = userService.getId();
        System.out.println(id);
    }
}
