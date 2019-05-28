package cn.it.yip.utils;

import com.sun.istack.internal.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:15
 **/
public class ClassUtils {
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap(8);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap(8);
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            //获取当前线程的context class loader
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {
            // 如果没有context loader，使用当前类的类加载器；
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // 如果当前类加载器无法获取，获得bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }
        return cl;
    }
    public static boolean isAssignableValue(Class<?> type, Object value) {
        return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
    }
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        } else {
            Class resolvedPrimitive;
            if (lhsType.isPrimitive()) {
                resolvedPrimitive = (Class)primitiveWrapperTypeMap.get(rhsType);
                if (lhsType == resolvedPrimitive) {
                    return true;
                }
            } else {
                resolvedPrimitive = (Class)primitiveTypeToWrapperMap.get(rhsType);
                if (resolvedPrimitive != null && lhsType.isAssignableFrom(resolvedPrimitive)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String convertResourcePathToClassName(String resourcePath) {
        return resourcePath.replace('/', '.');
    }

    public static String convertClassNameToResourcePath(String className) {
        return className.replace('.', '/');
    }

}
