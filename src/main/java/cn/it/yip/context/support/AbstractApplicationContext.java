package cn.it.yip.context.support;

import cn.it.yip.beans.factory.support.DeafaultBeanFactory;
import cn.it.yip.beans.xml.XmlBeanDefinitionReader;
import cn.it.yip.context.ApplicationContext;
import cn.it.yip.core.io.Resource;
import cn.it.yip.utils.ClassUtils;

/**
 * 使用模版方法设计模式抽取不变的代码
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:46
 **/
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DeafaultBeanFactory beanFactory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        this.beanFactory = new DeafaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.beanFactory);
        Resource resource = this.getResourcePath(configFile);
        reader.loadBeanDefinition(resource);
    }

    @Override
    public Object getBean(String beanID) {
        return this.beanFactory.getBean(beanID);
    }

    @Override
    public void setClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    protected abstract Resource getResourcePath(String path);
}
