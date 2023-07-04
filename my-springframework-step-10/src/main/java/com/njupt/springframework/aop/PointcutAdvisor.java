package com.njupt.springframework.aop;

import org.aopalliance.aop.Advice;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
