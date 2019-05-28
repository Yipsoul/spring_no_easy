package cn.it.yip.beans.exception;

import lombok.Getter;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 15:58
 **/
@Getter
public class BeanCreationException extends BeanException {
    private String beanName;

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '"+beanName+"' : "+msg);
        this.beanName = beanName;
    }
}
