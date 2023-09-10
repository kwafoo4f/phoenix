package com.kuafoo4j.phoenix.commom.core.pojo;

import lombok.Data;

/**
 * @description: 实例
 * @author: zk
 * @date: 2023-09-10 11:49
 */
@Data
public class Instance {
    /**
     * 实例Id
     */
    private String instanceId;
    /**
     * 实例名称
     */
    private String name;
    /**
     * 实例ip
     */
    private String ip;
    /**
     * 实例端口
     */
    private int port;
    /**
     * 实例健康状态
     */
    private boolean healthy = true;
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * 服务名称
     */
    private String serviceName;

}
