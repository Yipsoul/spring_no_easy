package cn.it.yip.test;

import cn.it.yip.beans.SimpleTypeConverter;
import cn.it.yip.beans.TypeConverter;
import cn.it.yip.beans.propertyeditors.CustomBooleanEditor;
import cn.it.yip.beans.propertyeditors.CustomNumberEditor;
import cn.it.yip.context.ApplicationContext;
import cn.it.yip.context.support.ClassPathXmlApplicationContext;
import cn.it.yip.service.StudentService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 10:41
 **/
public class CustomTest {

    @Test
    public void customNumberTest() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("123");
        Object value = editor.getValue();
        Assert.assertEquals(123, value);

    }

    @Test
    public void customBooleanTest() {
        CustomBooleanEditor customBooleanEditor = new CustomBooleanEditor(true);
        customBooleanEditor.setAsText("no");
        Object value = customBooleanEditor.getValue();
        Assert.assertEquals(false, value);
    }

    @Test
    public void typeConverterTest() {
        TypeConverter typeConverter = new SimpleTypeConverter();
        Double value = typeConverter.convertIfNecessary("123.5", Double.class);
        Assert.assertTrue(123.5 == value);
        Boolean aFalse = typeConverter.convertIfNecessary("false", Boolean.class);
        Assert.assertTrue(false == aFalse);
        Integer integer = typeConverter.convertIfNecessary("123", int.class);
        Assert.assertEquals(123,integer.intValue());
    }

    @Test
    public void typeConverterTest02() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        StudentService studentService = (StudentService) ctx.getBean("studentService");
        Assert.assertEquals("小叶同学",studentService.getName());
    }
}
