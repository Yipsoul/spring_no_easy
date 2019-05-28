package cn.it.yip.test;

import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.core.io.ClassResourcesLoader;
import cn.it.yip.core.io.Resource;
import cn.it.yip.service.StudentService;
import org.junit.Assert;
import org.junit.Test;


/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-27 14:53
 **/
public class ApplicationContextAnnotationTest {
    @Test
    public void testClassPathResources() {
        ClassResourcesLoader classResourcesLoader = new ClassResourcesLoader();
        Resource[] resources = classResourcesLoader.getResources("cn.it.yip.service");
        Assert.assertEquals(2, resources.length);
    }

    @Test
    public void testAnnotation() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext4.xml");
        StudentService studentService = (StudentService) ctx.getBean("studentService");
        Assert.assertNotNull(studentService.getTeacherDao());
        Assert.assertNotNull(studentService.getStudentDao());
    }
}
