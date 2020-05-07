package com.example.util.exception;

/**
 * @author NGX
 * @Date 2020/5/4 21:07
 */
public class LoginAccountAlreadlyInUse extends RuntimeException{
    private static final long serialVersionUID = -6669038994449838657L;

    public LoginAccountAlreadlyInUse() {
    }

    public LoginAccountAlreadlyInUse(String message) {
        super(message);
    }
}
