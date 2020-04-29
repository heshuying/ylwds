package com.hailian.ylwmall.common;

public class B2BMallException extends RuntimeException {

    public B2BMallException() {
    }

    public B2BMallException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new B2BMallException(message);
    }

}
