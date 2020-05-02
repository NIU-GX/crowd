package com.example.util.exception;

/**
 * @author NGX
 * @Date 2020/5/1 22:38
 * @Description 登陆失败抛出异常
 */
public class LoginFailedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
