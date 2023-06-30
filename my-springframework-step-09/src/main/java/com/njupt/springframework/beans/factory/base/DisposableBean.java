package com.njupt.springframework.beans.factory.base;

public interface DisposableBean {

    void destroy() throws Exception;
}
