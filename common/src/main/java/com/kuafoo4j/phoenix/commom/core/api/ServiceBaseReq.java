package com.kuafoo4j.phoenix.commom.core.api;

import lombok.Data;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:35
 */
@Data
public class ServiceBaseReq {
    /**
     *
     */
    private String namespaceId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * 服务名称
     */
    private String serviceName;


}
