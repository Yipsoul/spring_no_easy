package cn.it.yip.context;

import cn.it.yip.beans.factory.config.ConfigurableBeanFactory;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:18
 **/
public interface ApplicationContext extends ConfigurableBeanFactory {
    Object getBean(String beanID);
}
