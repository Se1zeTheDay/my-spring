package com.njupt.springframework.beans.factory.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建出一个用于传递类中属性信息的类，因为属性可能会有很多，所以还需要定义一个集合包装下。
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        // 创建PropertyValue类型的数组，并将list转为array
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

}
