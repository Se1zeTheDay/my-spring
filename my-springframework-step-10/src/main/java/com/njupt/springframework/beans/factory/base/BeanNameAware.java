package com.njupt.springframework.beans.factory.base;

public interface BeanNameAware extends Aware {

    void setBeanName(String name);
}
