package com.njupt.springframework.utils;

import com.njupt.springframework.beans.exception.BeansException;
public class ClassUtils {

    public static ClassLoader getDefaultClassLoader () {
        ClassLoader c1 = null;

        try {
            // 获取线程上下文加载器
            c1 = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
            throw new BeansException("cannot access thread context Classloader");
        }

        if (c1 == null) {
            c1 = ClassUtils.class.getClassLoader();
        }
        return c1;

    }

    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("&&"));
    }
}
