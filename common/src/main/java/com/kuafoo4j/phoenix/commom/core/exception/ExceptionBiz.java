package com.kuafoo4j.phoenix.commom.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:49
 */
@Getter
@AllArgsConstructor
public enum ExceptionBiz {
    SYS_ERR(1001,"系统异常!"),
    NO_SUCH_SERVICE(1002,"没有这样的服务!");


    private int code;
    private String message;

}
