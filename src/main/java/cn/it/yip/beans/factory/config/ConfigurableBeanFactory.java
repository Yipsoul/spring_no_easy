package cn.it.yip.beans.factory.config;

import cn.it.yip.beans.factory.BeanFactory;

/**
 * 将设置类加载器抽取成一个接口
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 12:35
 **/
public interface ConfigurableBeanFactory extends BeanFactory {

    void setClassLoader(ClassLoader beanClassLoader);

    ClassLoader getClassLoader();
}
