package cn.it.yip.beans.exception;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-21 11:03
 **/
public class TypeMismatchException extends RuntimeException {
    public TypeMismatchException() {
    }

    public TypeMismatchException(String message) {
        super(message);
    }

    public TypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
