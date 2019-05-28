package cn.it.yip.core.io;

import cn.it.yip.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:06
 **/
public class ClassPathResource implements Resource {
    private String path;
    private ClassLoader classLoader;
    public ClassPathResource(String path) {
        this(path,(ClassLoader) null);

    }
    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream in = this.classLoader.getResourceAsStream(path);
        if(in == null)
            throw new FileNotFoundException(path + "open fail....");
        return in;
    }

    @Override
    public String getDescription() {
        return path;
    }
}
