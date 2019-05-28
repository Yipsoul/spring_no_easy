package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.factory.BeanFactory;
import cn.it.yip.beans.factory.config.RuntimeBeanReference;
import cn.it.yip.beans.factory.config.TypedStringValue;

/**
 * 根据propertyValue里的value属性生成对应的值
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-20 23:55
 **/
public class BeanDefinitionResolver {

    private final BeanFactory factory;

    public BeanDefinitionResolver(BeanFactory factory) {
        this.factory = factory;
    }

    /**
     * 解析对象的类型并返回实例
     *
     * @param value
     * @return
     */
    public Object resolveBeanIfNecessary(Object value) {
        if (value == null)
            return null;

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            return this.factory.getBean(ref.getBeanName());
        } else if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        } else {
            //todo
            throw new RuntimeException("the value " + value + "has not implemented");
        }
    }
}
