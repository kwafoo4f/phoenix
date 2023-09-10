package com.kuafoo4j.phoenix.commom.core.exception;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:48
 */
public class PhoenixException extends RuntimeException {

    public PhoenixException(String message) {
        super(message);
    }

    public PhoenixException(ExceptionBiz exceptionBiz) {
        super(exceptionBiz.getMessage());
    }
}
