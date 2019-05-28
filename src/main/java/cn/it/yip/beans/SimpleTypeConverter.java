package cn.it.yip.beans;

import cn.it.yip.beans.exception.TypeMismatchException;
import cn.it.yip.beans.propertyeditors.CustomBooleanEditor;
import cn.it.yip.beans.propertyeditors.CustomNumberEditor;
import cn.it.yip.utils.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换实现类
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 11:11
 **/
public class SimpleTypeConverter implements TypeConverter {

    private final Map<Class<?>, PropertyEditor> commonTypesCache = new HashMap<>(32);

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {

        if (ClassUtils.isAssignableValue(requiredType, value)) {
            return (T) value;
        } else {
            if ((value instanceof String)) {
                PropertyEditor editor = findDefaultEditor(requiredType);
                editor.setAsText((String) value);
                return (T) editor.getValue();
            } else {
                throw new TypeMismatchException("can't converter value:" + value + " " + requiredType);
            }

        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        PropertyEditor editor = this.findValue(requiredType);
        if (editor == null) {
            throw new RuntimeException("Editor for " + requiredType + "has not been implemented");
        }
        return editor;
    }

    private <T> PropertyEditor findValue(Class<T> requiredType) {
        if (this.commonTypesCache.isEmpty()) {
            createCommonTypesCache();
        }
        return this.commonTypesCache.get(requiredType);
    }

    private void createCommonTypesCache() {
        commonTypesCache.put(int.class, new CustomNumberEditor(Integer.class, false));
        commonTypesCache.put(double.class, new CustomNumberEditor(Double.class, false));
        commonTypesCache.put(float.class, new CustomNumberEditor(Float.class, false));
        commonTypesCache.put(byte.class, new CustomNumberEditor(Byte.class, false));
        commonTypesCache.put(short.class, new CustomNumberEditor(Short.class, false));
        commonTypesCache.put(Short.class, new CustomNumberEditor(Short.class, true));
        commonTypesCache.put(Byte.class, new CustomNumberEditor(Byte.class, true));
        commonTypesCache.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        commonTypesCache.put(Float.class, new CustomNumberEditor(Float.class, true));
        commonTypesCache.put(Double.class, new CustomNumberEditor(Double.class, true));
        commonTypesCache.put(boolean.class, new CustomBooleanEditor(true));
        commonTypesCache.put(Boolean.class, new CustomBooleanEditor(true));
    }
}
