package cn.it.yip.beans;

import cn.it.yip.beans.factory.config.ConstructorArgumentValues;

import java.util.List;

/**
 * 存储一个bean定义的属性相关接口
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:21
 **/
public interface BeanDefinition {
    static final String SCOPE_SINGLETON = "singleton";
    static final String SCOPE_PROTOTYPE = "prototype";
    static final String SCOPE_DEFAULT = "";

    String getID();
    boolean isSingleton();

    boolean isPrototype();

    void setScope(String scope);

    String getScope();

    String getClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgumentValues getConstructorArgumentValues();

    boolean hasConstructorArgumentValues();
}
