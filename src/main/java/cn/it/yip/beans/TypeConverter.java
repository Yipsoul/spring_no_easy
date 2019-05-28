package cn.it.yip.beans;

import cn.it.yip.beans.exception.TypeMismatchException;

/**
 * 类型转换接口
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 11:04
 **/
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requierType) throws TypeMismatchException;
}
