package cn.it.yip.test;

import cn.it.yip.beans.exception.BeanCreationException;
import cn.it.yip.beans.exception.BeanDenfinitionStoreException;
import cn.it.yip.beans.factory.support.DeafaultBeanFactory;
import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.xml.XmlBeanDefinitionReader;
import cn.it.yip.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:13
 **/
public class BeanFactoryTest {
    public DeafaultBeanFactory beanFactroy = null;
    public XmlBeanDefinitionReader reader = null;

    @Before
    public void beforeLoader() {
        beanFactroy = new DeafaultBeanFactory();
        reader = new XmlBeanDefinitionReader(beanFactroy);

    }

    @Test
    public void test01() {
        reader.loadBeanDefinition("applicationContext.xml");
        BeanDefinition beanDefinition = beanFactroy.getBeanDefinition("userService");
        Assert.assertEquals("cn.it.yip.service.UserService", beanDefinition.getClassName());
        UserService userService = (UserService) beanFactroy.getBean("userService");
        Assert.assertNotNull(userService);
        userService.hello();
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition("applicationContext.xml");
        try {
            UserService service = (UserService) beanFactroy.getBean("in");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testXML() {
        try {
            reader.loadBeanDefinition("xxx");
        } catch (BeanDenfinitionStoreException e) {
            return;
        }
        Assert.fail();
    }


    @Test
    public void testBeanScope(){
        reader.loadBeanDefinition("applicationContext.xml");
        BeanDefinition beanDefinition = beanFactroy.getBeanDefinition("userService");
        Assert.assertTrue(beanDefinition.isSingleton());
        Assert.assertFalse(beanDefinition.isPrototype());

        UserService bean = (UserService) beanFactroy.getBean("userService");
        Assert.assertNotNull(bean);
        UserService bean2 = (UserService) beanFactroy.getBean("userService");
        Assert.assertTrue(bean.equals(bean2));
    }

}
