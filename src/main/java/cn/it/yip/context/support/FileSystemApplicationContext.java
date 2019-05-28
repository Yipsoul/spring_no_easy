package cn.it.yip.context.support;

import cn.it.yip.core.io.FileSystemResource;
import cn.it.yip.core.io.Resource;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:48
 **/
public class FileSystemApplicationContext extends AbstractApplicationContext {
    public FileSystemApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourcePath(String path) {
        return new FileSystemResource(path);
    }
}
