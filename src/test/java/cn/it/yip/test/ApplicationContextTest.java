package cn.it.yip.test;

import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.context.support.FileSystemApplicationContext;
import cn.it.yip.service.UserService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:23
 **/
public class ApplicationContextTest {
    @Test
    public void testApplicationContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        Assert.assertNotNull(userService);
    }

    @Test
    public void testFileSystemApplicationContext() {
        ApplicationContext ctx = new FileSystemApplicationContext("D:\\Idea-Project\\spring_easy\\src\\main\\resources\\applicationContext.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        Assert.assertNotNull(userService);
        userService.hello();
    }

}
