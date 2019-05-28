package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.BeanDefinition;

/**
 * Bean注册类接口
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 16:30
 **/
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registryBeanDefinition(String beanID,BeanDefinition beanDefinition);
}
