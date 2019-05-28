package cn.it.yip.beans.exception;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:56
 **/
public class BeanException extends RuntimeException {
    public BeanException(String msg){
        super(msg);
    }
    public BeanException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}
