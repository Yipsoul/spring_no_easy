package cn.it.yip.beans.factory.support;

import cn.it.yip.beans.BeanDefinition;
import cn.it.yip.beans.PropertyValue;
import cn.it.yip.beans.SimpleTypeConverter;
import cn.it.yip.beans.TypeConverter;
import cn.it.yip.beans.exception.BeanCreationException;
import cn.it.yip.beans.factory.BeanFactory;
import cn.it.yip.beans.factory.config.ConfigurableBeanFactory;
import cn.it.yip.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:19
 **/
@Slf4j
public class DeafaultBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry, ConfigurableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ClassLoader beanClassLoader;

    /**
     * 获取bean的属性
     *
     * @param beanID
     * @return
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * 根据id获取Bean
     *
     * @param beanID
     * @return
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanID);
        if (beanDefinition == null) {
            log.error("BeanDefinition is not exist");
            throw new BeanCreationException("BeanDefinition is not exist");
        }
        //判断该bean是否为单例
        if (beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(beanDefinition);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        //多例
        return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition) {
        //创建bean
        Object bean = instantiateBean(beanDefinition);
        //设置属性
        populateBean(bean, beanDefinition);
        return bean;
    }

    /**
     * 设置bean属性
     *
     * @param bean           要设置属性的bean对象
     * @param beanDefinition bean对象的信息
     */
    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        //获取bean里面的所有property标签
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        if (propertyValues == null || propertyValues.isEmpty())
            return;
        //创建生成beanproperty类
        BeanDefinitionResolver resolver = new BeanDefinitionResolver(this);
        //自定义类型转换
        TypeConverter converter = new SimpleTypeConverter();
        try {
            //遍历property属性标签
            for (PropertyValue s : propertyValues) {
                //获取name
                String propertyName = s.getName();
                //获取name对应的value
                Object propertyValue = resolver.resolveBeanIfNecessary(s.getValue());
                //获得javabean的所有信息
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                //获取该javabean的set/get属性
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : propertyDescriptors) {
                    //如果propertyName和该data的Name相同。则可以设置属性
                    if (StringUtils.equals(propertyName, descriptor.getName())) {
                        Object converterValue = converter.convertIfNecessary(propertyValue, descriptor.getPropertyType());
                        descriptor.getWriteMethod().invoke(bean, converterValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("load javabeanInfo fail", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建bean实例对象
     *
     * @param className
     * @return
     */
    private Object instantiateBean(BeanDefinition beanDefinition) {
        if (beanDefinition.hasConstructorArgumentValues()) {
            ConstructResolver constructResolver = new ConstructResolver(this);
            return constructResolver.autowireConstructor(beanDefinition);
        } else {
            String className = beanDefinition.getClassName();
            ClassLoader cl = this.getClassLoader();
            try {
                Class<?> clazz = cl.loadClass(className);
                return clazz.newInstance();
            } catch (Exception e) {
                log.error("create bean for" + className + "fail" + e.getMessage());
                throw new BeanCreationException("create bean for" + className + "fail" + e.getMessage());
            }
        }
    }

    /**
     * 注册bean到map容器当中
     *
     * @param beanID
     * @param beanDefinition
     */
    @Override
    public void registryBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }

    @Override
    public void setClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
