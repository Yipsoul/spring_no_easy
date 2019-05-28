package cn.it.yip.utils;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:14
 **/
public abstract class Assert {
    public static void NotNull(Object obj,String msg){
        if(obj == null){
            throw new IllegalArgumentException("msg");
        }
    }
}
