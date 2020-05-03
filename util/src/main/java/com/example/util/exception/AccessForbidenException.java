package com.example.util.exception;

/**
 * @author NGX
 * @Date 2020/5/3 16:12
 *
 * 访问被拒绝异常
 */
public class AccessForbidenException extends RuntimeException{

    private static final long serialVersionUID = -8717942616409998982L;

    public AccessForbidenException() {
        super();
    }

    public AccessForbidenException(String message) {
        super(message);
    }
}
