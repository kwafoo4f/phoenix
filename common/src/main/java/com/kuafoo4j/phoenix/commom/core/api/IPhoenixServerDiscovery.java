package com.kuafoo4j.phoenix.commom.core.api;

import com.kuafoo4j.phoenix.commom.core.pojo.Instance;

import java.util.List;

/**
 * 服务查询
 */
public interface IPhoenixServerDiscovery {
    /**
     * 获取服务的所有实例
     * @param serviceName
     * @return
     */
    List<Instance> getInstanceList(String serviceName);
}
