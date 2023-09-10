package com.kuafoo4j.phoenix.commom.core.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 服务信息
 * @author: zk
 * @date: 2023-09-10 11:42
 */
@Data
public class ServiceInfo {
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 服务名称
     */
    private String name;
    /**
     * 集群名称
     */
    private String clusters;
    /**
     * 实例主机
     */
    private List<Instance> hosts = new ArrayList<>();

}
