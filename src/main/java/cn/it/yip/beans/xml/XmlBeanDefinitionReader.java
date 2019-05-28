package cn.it.yip.beans.xml;

import cn.it.yip.beans.PropertyValue;
import cn.it.yip.beans.exception.BeanDenfinitionStoreException;
import cn.it.yip.beans.factory.config.ConstructorArgumentValues;
import cn.it.yip.beans.factory.config.RuntimeBeanReference;
import cn.it.yip.beans.factory.config.TypedStringValue;
import cn.it.yip.beans.factory.support.BeanDefinitionRegistry;
import cn.it.yip.beans.factory.support.GenericBeanDefinition;
import cn.it.yip.core.io.Resource;
import cn.it.yip.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 单一职责原则 专门读取xml配置文件
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 16:28
 **/
@Slf4j
public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    private static final String TYPE_ATTRIBUTE = "type";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 加载配置文件
     *
     * @param configFile
     */
    public void loadBeanDefinition(String configFile) {
        InputStream in = null;
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        in = cl.getResourceAsStream(configFile);
        loadXml(in);
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadXml(inputStream);
    }

    /**
     * 解析xml配置
     *
     * @param in
     */
    private void loadXml(InputStream in) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(in);
            //beans
            Iterator<Element> elementIterator = doc.getRootElement().elementIterator();
            while (elementIterator.hasNext()) {
                Element next = elementIterator.next();
                String id = next.attributeValue(ID_ATTRIBUTE);
                String className = next.attributeValue(CLASS_ATTRIBUTE);
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition(id, className);
                if (next.attributeValue(SCOPE_ATTRIBUTE) != null) {
                    beanDefinition.setScope(next.attributeValue(SCOPE_ATTRIBUTE));
                }
                //解析构造参数标签
                parseConstructorElements(next, beanDefinition);
                //解析property标签
                parsePropertyElement(next, beanDefinition);
                this.registry.registryBeanDefinition(id, beanDefinition);
            }
        } catch (DocumentException e) {
            throw new BeanDenfinitionStoreException("xml load fail....", e);
        }

    }

    /**
     * 解析construct-arg标签参数并进行封装
     *
     * @param next
     * @param beanDefinition
     */
    private void parseConstructorElements(Element next, GenericBeanDefinition beanDefinition) {
        Iterator<Element> iterator = next.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element argElment = iterator.next();
            parseConstructorElement(argElment, beanDefinition);
        }
    }

    /**
     * 封装一个constructor-arg标签
     *
     * @param argElment
     * @param beanDefinition
     */
    private void parseConstructorElement(Element argElment, GenericBeanDefinition beanDefinition) {
        String name = argElment.attributeValue(NAME_ATTRIBUTE);
        String type = argElment.attributeValue(TYPE_ATTRIBUTE);
        Object value = parsePropertyValue(argElment);
        ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(value);
        if (StringUtils.isNotBlank(name)) {
            valueHolder.setName(name);
        }
        if (StringUtils.isNotBlank(type)) {
            valueHolder.setType(type);
        }
        beanDefinition.getConstructorArgumentValues().addArgumentValue(valueHolder);
    }

    /**
     * 解析property标签
     *
     * @param next
     * @param beanDefinition
     */
    private void parsePropertyElement(Element next, GenericBeanDefinition beanDefinition) {
        Iterator<Element> iterator = next.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element popElement = iterator.next();
            String propertyName = popElement.attributeValue(NAME_ATTRIBUTE);
            if (StringUtils.isBlank(propertyName)) {
                log.error("property Tag must have a 'name' attribute");
                return;
            }
            //获取value对象
            Object value = parsePropertyValue(popElement);
            //封装到一个property对象中
            PropertyValue pv = new PropertyValue(propertyName, value);
            //将一个bean里面的所有porperty标签属性放入list中
            beanDefinition.getPropertyValueList().add(pv);
        }
    }

    /**
     * 解析每个property的value
     *
     * @param popElement
     */
    private Object parsePropertyValue(Element popElement) {
        boolean refBoolean = (popElement.attribute(REF_ATTRIBUTE) != null);
        boolean valueBoolean = (popElement.attribute(VALUE_ATTRIBUTE) != null);
        //如果是ref標簽
        if (refBoolean) {
            return new RuntimeBeanReference(popElement.attributeValue(REF_ATTRIBUTE));
            //如果是value標簽
        } else if (valueBoolean) {
            return new TypedStringValue(popElement.attributeValue(VALUE_ATTRIBUTE));
        } else {
            log.error("Property Tag must be a 'ref' or a 'value'");
            throw new RuntimeException("Property Tag must be a 'ref' or a 'value'");
        }
    }
}
