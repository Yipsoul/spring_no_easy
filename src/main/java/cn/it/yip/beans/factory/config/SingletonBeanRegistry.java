package cn.it.yip.beans.factory.config;

/**
 * bean单例的接口
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 13:06
 **/
public interface SingletonBeanRegistry {
    //注册单例bean
    void registerSingleton(String beanID, Object bean);

    //通过beanID获取当前单例bean
    Object getSingleton(String beanID);
}
