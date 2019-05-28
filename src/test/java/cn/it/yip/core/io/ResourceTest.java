package cn.it.yip.core.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:31
 **/
public class ResourceTest {

    @Test
    public void classPathResourceGetInputStream() throws IOException {
        Resource r = new ClassPathResource("applicationContext.xml");
        Assert.assertNotNull(r.getInputStream());
    }

    @Test
    public void fileSystemResourceGetInputSteam() throws IOException {
        Resource r =  new FileSystemResource("D:\\Idea-Project\\spring_easy\\src\\main\\resources\\applicationContext.xml");
        Assert.assertNotNull(r.getInputStream());
    }
}