package cn.it.yip.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:05
 **/
public interface Resource {
    InputStream getInputStream() throws IOException;
    String getDescription();
}
