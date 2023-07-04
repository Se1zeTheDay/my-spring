package com.njupt;


import com.njupt.bean.*;
import com.njupt.springframework.aop.AdvisedSupport;
import com.njupt.springframework.aop.Cglib2AopProxy;
import com.njupt.springframework.aop.JDKDynamicAopProxy;
import com.njupt.springframework.aop.TargetSource;
import com.njupt.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.njupt.springframework.context.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

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

    @Test
    public void test_aop() throws NoSuchMethodException {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());

        // true、true
    }

    @Test
    public void test_ref() {

        IUserService userService2 = new UserService2();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService2));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.njupt.bean.IUserService.*(..))"));

        IUserService proxy_jdk = (IUserService) new JDKDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());
        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("花花"));

    }



}
