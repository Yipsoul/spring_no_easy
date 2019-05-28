package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.factory.config.RuntimeBeanReference;
import cn.it.yip.beans.xml.XmlBeanDefinitionReader;
import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.dao.StudentDao;
import cn.it.yip.dao.TeacherDao;
import cn.it.yip.service.StudentService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 00:06
 **/
public class BeanDefinitionResolverTest {

    @Test
    public void resolveBeanIfNecessary() {
        DeafaultBeanFactory factory = new DeafaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition("applicationContext2.xml");
        BeanDefinitionResolver resolver = new BeanDefinitionResolver(factory);
        StudentDao studentDao = (StudentDao) resolver.resolveBeanIfNecessary(new RuntimeBeanReference("studentDao"));
        Assert.assertNotNull(studentDao);

    }
    @Test
    public void resolveBeanValue(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        StudentService studentService = (StudentService) ctx.getBean("studentService");
        Assert.assertNotNull(studentService.getStudentDao());
        Assert.assertNotNull(studentService.getTeacherDao());
        Assert.assertTrue(studentService.getTeacherDao() instanceof TeacherDao);
        Assert.assertTrue(studentService.getStudentDao() instanceof StudentDao);
        Assert.assertEquals("小叶同学",studentService.getName());
    }



}