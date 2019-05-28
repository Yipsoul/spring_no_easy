package cn.it.yip.context.support;

import cn.it.yip.core.io.ClassPathResource;
import cn.it.yip.core.io.Resource;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:19
 **/
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }
    @Override
    protected Resource getResourcePath(String path) {
        return new ClassPathResource(path,this.getClassLoader());
    }
}
