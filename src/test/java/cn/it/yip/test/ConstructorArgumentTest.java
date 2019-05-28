package cn.it.yip.test;

import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.factory.config.ConstructorArgumentValues;
import cn.it.yip.beans.factory.config.RuntimeBeanReference;
import cn.it.yip.beans.factory.config.TypedStringValue;
import cn.it.yip.beans.factory.support.ConstructResolver;
import cn.it.yip.beans.factory.support.DeafaultBeanFactory;
import cn.it.yip.beans.xml.XmlBeanDefinitionReader;
import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.service.StudentService;
import org.junit.Assert;
import org.junit.Test;

/**
 * 构造函数初始化bean 测试
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-22 17:13
 **/
public class ConstructorArgumentTest {

    @Test
    public void constructArgument() {
        DeafaultBeanFactory deafaultBeanFactory = new DeafaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(deafaultBeanFactory);
        reader.loadBeanDefinition("applicationContext3.xml");
        BeanDefinition beanDefinition = deafaultBeanFactory.getBeanDefinition("studentService");
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
        Assert.assertEquals(4, constructorArgumentValues.getSize());
        RuntimeBeanReference ref1 = (RuntimeBeanReference) constructorArgumentValues.getArgumentValues().get(0).getValue();
        Assert.assertEquals("studentDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) constructorArgumentValues.getArgumentValues().get(1).getValue();
        Assert.assertEquals("teacherDao", ref2.getBeanName());
        TypedStringValue stringValue = (TypedStringValue) constructorArgumentValues.getArgumentValues().get(2).getValue();
        Assert.assertEquals("小叶同学", stringValue.getValue());
    }


    @Test
    public void constructArgCreateBean() {
        DeafaultBeanFactory beanFactory = new DeafaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition("applicationContext3.xml");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("studentService");
        ConstructResolver constructResolver = new ConstructResolver(beanFactory);
        StudentService studentService = (StudentService) constructResolver.autowireConstructor(beanDefinition);
        Assert.assertNotNull(studentService);
        Assert.assertNotNull(studentService.getStudentDao());
        Assert.assertNotNull(studentService.getTeacherDao());
        Assert.assertEquals("小叶同学", studentService.getName());
        Assert.assertEquals(21, studentService.getAge());
    }

    @Test
    public void ApplicationContextContructorCreateBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext3.xml");
        StudentService studentService = (StudentService) ctx.getBean("studentService");
        Assert.assertNotNull(studentService);
        Assert.assertNotNull(studentService.getStudentDao());
        Assert.assertNotNull(studentService.getTeacherDao());
        Assert.assertEquals("小叶同学", studentService.getName());
        Assert.assertEquals(21, studentService.getAge());

    }
}
