package com.kuafoo4j.phoenix.commom.core.pojo;

import lombok.Data;

import java.util.Objects;

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
     *
     */
    private String namespaceId;
    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 最后一次心跳
     */
    private Long lastBeat = System.currentTimeMillis();

    /**
     * 更新心跳
     */
    public void updateBeat() {
        lastBeat = System.currentTimeMillis();
        if (!healthy) {
            this.setHealthy(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return port == instance.port && healthy == instance.healthy && Objects.equals(instanceId, instance.instanceId) && Objects.equals(name, instance.name) && Objects.equals(ip, instance.ip) && Objects.equals(namespaceId, instance.namespaceId) && Objects.equals(clusterName, instance.clusterName) && Objects.equals(serviceName, instance.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId, name, ip, port, healthy, namespaceId, clusterName, serviceName);
    }
}
