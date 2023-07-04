package com.njupt.springframework.beans.factory.support;

import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.BeanPostProcessor;
import com.njupt.springframework.beans.factory.base.ConfigurableBeanFactory;
import com.njupt.springframework.beans.factory.base.FactoryBean;
import com.njupt.springframework.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    /**
     * template method设计模式
     * 获得bean
     * @param beanName
     * @return
     */
    @Override
    public Object getBean(String beanName) {
        return getBean(beanName, (Object) null);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        Object sharedInstance = getSingleton(beanName);

        if (sharedInstance != null) {
            return getObjectForBeanInstance(sharedInstance, beanName);
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);

        // 添加对FactoryBean的判断
        return getObjectForBeanInstance(bean, beanName);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {

        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 先从cache中寻找factoryBean
        Object object = getCachedObjectForFactoryBean(beanName);

        // 寻找不到则重新从FactoryBean中创建
        if (object == null) {
            object = getObjectFromFactoryBean(((FactoryBean<?>) beanInstance), beanName);
        }

        return object;

    }

    public <T> T getBean(String name, Class<T> requiredType){
        return (T) getBean(name);
    }

    /**
     * 通过beanDefinition创建bean
     * @param beanName
     * @param beanDefinition
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    /**
     * 获取beanDefinition
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }


    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }
}
