package com.kuafoo4j.phoenix.commom.core.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @description: 服务
 * @author: zk
 * @date: 2023-09-10 11:42
 */
@Data
public class Service {
    /**
     *
     */
    private String namespaceId;
    /**
     *
     */
    private String groupName;
    /**
     * 服务名称
     */
    private String name;
    /**
     * 集群， Map<Cluster::getName,Cluster>
     */
    private Map<String,Cluster> clusterMap;

}
