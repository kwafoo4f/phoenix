package com.kuafoo4j.phoenix.commom.core.pojo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 集群
 * @author: zk
 * @date: 2023-09-10 11:47
 */
@Data
public class Cluster {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 集群名称
     */
    private String name;
    /**
     * 服务
     */
    private Service service;
    /**
     * 集群下的实例
     */
    private Set<Instance> ephemeralInstances = new HashSet<Instance>();

}
