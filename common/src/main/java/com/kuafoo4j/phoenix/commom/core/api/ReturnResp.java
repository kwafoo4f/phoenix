package com.kuafoo4j.phoenix.commom.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnResp<T> {
    private static final int SUCCESS = 200;
    private static final String MES = "SUCCESS";

    private int code = SUCCESS;
    private String message;
    private T date;

    public static <T> ReturnResp<T> ok(T date) {
        return new ReturnResp(SUCCESS, MES, date);
    }

    public static ReturnResp ok() {
        return new ReturnResp(SUCCESS, MES, Collections.EMPTY_MAP);
    }

    public static ReturnResp failed(int code, String message) {
        return new ReturnResp(code, message, Collections.EMPTY_MAP);
    }

}
