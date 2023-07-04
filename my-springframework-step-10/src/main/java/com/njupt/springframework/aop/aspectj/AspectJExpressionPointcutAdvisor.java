package com.njupt.springframework.aop.aspectj;

import com.njupt.springframework.aop.Pointcut;
import com.njupt.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 表达式
    private String expression;

    // 具体的拦截方法
    private Advice advice;

    // 切面
    private AspectJExpressionPointcut pointcut;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
