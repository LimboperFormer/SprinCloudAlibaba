package com.lee.exception;

/**
 * @Author lee
 * @Date 2021/1/25
 * @Version 1.0
 */
public class ThrowableException extends RuntimeException {

    public ThrowableException(String message) {
        super(message);
    }

    public static String fallbackMethod(String name,Throwable e) {
        return  "fallbackMethod";
    }
}
