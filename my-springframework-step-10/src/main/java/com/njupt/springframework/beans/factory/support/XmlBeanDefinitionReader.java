package com.njupt.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.beans.factory.base.BeanDefinition;
import com.njupt.springframework.beans.factory.base.BeanDefinitionRegistry;
import com.njupt.springframework.beans.factory.base.BeanReference;
import com.njupt.springframework.beans.factory.base.PropertyValue;
import com.njupt.springframework.core.io.Resource;
import com.njupt.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {


    // 此处调用抽象类构造方法并不是说创建抽象类实例，而是给抽象类初始化参数
    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }


    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream is = resource.getInputStream()) {
            doLoadBeanDefinition(is);
        } catch (ClassNotFoundException | IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();


        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);

            // 判断元素
            if (!(child instanceof Element)) continue;

            // 判断对象
            if (!("bean".equals(child.getNodeName()))) continue;

            // 解析标签
            Element bean = (Element) child;
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");


            //增加对init-method、destroy-method的读取
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");

            // bean scope
            String beanScope = bean.getAttribute("scope");

            Class<?> clazz = Class.forName(className);

            // 设置beanName
            // 优先级id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;

            // 若都为空，则默认beanName为类名的驼峰
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            // 设置到beanDefinition中
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StrUtil.isNotEmpty(beanName)) beanDefinition.setScope(beanScope);

            NodeList beanChildNodes = bean.getChildNodes();

            for (int j = 0; j < beanChildNodes.getLength(); j++) {
                Node beanChild = beanChildNodes.item(j);

                // 判断元素
                if (!(beanChild instanceof Element)) continue;

                // 判断对象
                if (!("property".equals(beanChild.getNodeName()))) continue;

                Element property = (Element) beanChild;

                String attrName = property.getAttribute("name");

                String attrValue = property.getAttribute("value");

                // 引用别的bean
                String attrRef = property.getAttribute("ref");


                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);

            }
            // 注册beanDefinition
            BeanDefinitionRegistry beanDefinitionRegistry = getBeanDefinitionRegistry();

            if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);



        }


    }
}
