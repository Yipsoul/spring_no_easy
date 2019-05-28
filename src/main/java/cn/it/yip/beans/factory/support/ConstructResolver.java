package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.SimpleTypeConverter;
import cn.it.yip.beans.exception.BeanCreationException;
import cn.it.yip.beans.exception.TypeMismatchException;
import cn.it.yip.beans.factory.config.ConfigurableBeanFactory;
import cn.it.yip.beans.factory.config.ConstructorArgumentValues;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-22 23:11
 **/
@Slf4j
public class ConstructResolver {

    private final ConfigurableBeanFactory beanFactory;

    public ConstructResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 根据beanDefinition对象通过构造器注入属性
     *
     * @param bd
     * @return
     */
    public Object autowireConstructor(BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Class<?> classToUse;
        Object[] argToUse = null;
        try {
            //根据该对象的类全路径获取class
            classToUse = this.beanFactory.getClassLoader().loadClass(bd.getClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getID() + "instantiation of bean failed...", e);
        }
        //通过Class获取他的所有构造函数
        Constructor<?>[] constructors = classToUse.getConstructors();
        //获取beanDefinition对象的构造参数属性集合
        ConstructorArgumentValues argumentValues = bd.getConstructorArgumentValues();
        BeanDefinitionResolver resolver = new BeanDefinitionResolver(this.beanFactory);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        //遍历获取每个构造函数的参数数量是否与beanDefinition对象的ConstructArgument属性的数量是否相同
        for (int i = 0; i < constructors.length; i++) {
            Class<?>[] parameterTypes = constructors[i].getParameterTypes();
            //如果参数个数不相等，说明不是该构造函数
            if (parameterTypes.length != argumentValues.getSize()) {
                continue;
            }

            argToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(argToUse, parameterTypes, resolver, converter, argumentValues);
            //如果result为true，说明属性实例化并记录成功，直接跳出循环
            if (result) {
                constructorToUse = constructors[i];
                break;
            }
        }
        //判断是否能找到合适的构造函数
        if (constructorToUse == null)
            throw new BeanCreationException(bd.getID() + "find not appropriate constructor");
        try {
            return constructorToUse.newInstance(argToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getID() + "can't find a create instance bean or type fail.. constructor:" + constructorToUse + "  paramType:" + argToUse);
        }
    }

    /**
     * 根据正确的构造方法Class获取对应的Type属性列表
     *
     * @param argToUse       属性数组
     * @param parameterTypes 构造方法里的TypeClass
     * @param resolver       根据对象类型解析对象类
     * @param converter      类型转换类
     * @param argumentValues beanDefinition对象的构造参数属性
     * @return
     */
    private boolean valuesMatchTypes(Object[] argToUse, Class<?>[] parameterTypes, BeanDefinitionResolver resolver, SimpleTypeConverter converter, ConstructorArgumentValues argumentValues) {
        //遍历Type属性列表
        try {
            for (int i = 0; i < parameterTypes.length; i++) {
                //获取valueHolder
                ConstructorArgumentValues.ValueHolder valueHolder = argumentValues.getArgumentValues().get(i);
                //实例化对象
                Object valueResolver = resolver.resolveBeanIfNecessary(valueHolder.getValue());

                //判断对象是否需要转型
                Object ValueConverter = converter.convertIfNecessary(valueResolver, parameterTypes[i]);
                //将对象记录到argToUse数组中
                argToUse[i] = ValueConverter;
            }
        } catch (TypeMismatchException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
