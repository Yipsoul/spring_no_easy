package cn.it.yip.test;

import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.PropertyValue;
import cn.it.yip.beans.factory.config.RuntimeBeanReference;
import cn.it.yip.beans.factory.config.TypedStringValue;
import cn.it.yip.beans.factory.support.DeafaultBeanFactory;
import cn.it.yip.beans.xml.XmlBeanDefinitionReader;
import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.service.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 15:24
 **/
public class ApplicationContextConstructorTest {
    public DeafaultBeanFactory beanFactroy = null;
    public XmlBeanDefinitionReader reader = null;

    @Before
    public void beforeLoader() {
        beanFactroy = new DeafaultBeanFactory();
        reader = new XmlBeanDefinitionReader(beanFactroy);

    }
    @Test
    public void testPropertyValues() {
        reader.loadBeanDefinition("applicationContext2.xml");
        BeanDefinition beanDefinition = beanFactroy.getBeanDefinition("studentService");
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        Assert.assertEquals(propertyValues.size(),4);
        {
                PropertyValue pv =  this.gerPropertyValue("studentDao",propertyValues);
                Assert.assertNotNull(pv);
                Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue pv =  this.gerPropertyValue("name",propertyValues);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof TypedStringValue);
        }
        {
            PropertyValue pv =  this.gerPropertyValue("teacherDao",propertyValues);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }


    }

    private PropertyValue gerPropertyValue(String beanID, List<PropertyValue> propertyValues) {
        List<PropertyValue> collect = propertyValues.stream().filter(s -> s.getName().equals(beanID)).collect(Collectors.toList());
        return (collect.size() != 0 ? collect.get(0) : null);
    }
}
