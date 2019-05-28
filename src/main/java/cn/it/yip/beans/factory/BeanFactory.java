package cn.it.yip.beans.factory;


/**
 * 获取bean的工厂接口
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:19
 **/
public interface BeanFactory {

    Object getBean(String beanID);
}
