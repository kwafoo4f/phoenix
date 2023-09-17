package com.kuafoo4j.phoenix.commom.core.api;

import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import lombok.Data;

/**
 * @description: 心跳请求
 * @author: zk
 * @date: 2023-09-10 20:07
 */
@Data
public class BeatReq extends ServiceBaseReq {
    /**
     * 实例
     */
    private Instance instance;
}
