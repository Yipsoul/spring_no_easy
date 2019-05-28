package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.PropertyValue;
import cn.it.yip.beans.factory.config.ConstructorArgumentValues;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericBeanDefinition implements BeanDefinition {
    private String beanID;
    private String beanClassName;
    //单例
    private boolean singleton = true;
    //多例
    private boolean prototype = false;

    private String scope;

    private List<PropertyValue> propertyValueList = new ArrayList<>();
    private ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();

    @Override
    public String getID() {
        return this.beanID;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public GenericBeanDefinition(String beanID, String beanClassName) {
        this.beanID = beanID;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public String getClassName() {
        return this.beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

    @Override
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return this.constructorArgumentValues;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.getConstructorArgumentValues().isEmpty();
    }
}
