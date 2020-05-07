package com.example.util.exception;

/**
 * @author NGX
 * @Date 2020/5/4 21:07
 */
public class LoginAccountAlreadlyInUseForUpdate extends RuntimeException{
    private static final long serialVersionUID = -6669038994449838657L;

    public LoginAccountAlreadlyInUseForUpdate() {
    }

    public LoginAccountAlreadlyInUseForUpdate(String message) {
        super(message);
    }

    public LoginAccountAlreadlyInUseForUpdate(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAccountAlreadlyInUseForUpdate(Throwable cause) {
        super(cause);
    }

    public LoginAccountAlreadlyInUseForUpdate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
