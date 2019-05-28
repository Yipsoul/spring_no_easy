package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认单例注册类
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 13:08
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanID, Object bean) {
        Object obj = singletonBeanMap.get(beanID);
        if (obj != null) {
            throw new IllegalStateException("bean is exist... -> beanID : " + beanID);
        }
        singletonBeanMap.put(beanID, bean);
    }

    @Override
    public Object getSingleton(String beanID) {
        return this.singletonBeanMap.get(beanID);
    }
}
