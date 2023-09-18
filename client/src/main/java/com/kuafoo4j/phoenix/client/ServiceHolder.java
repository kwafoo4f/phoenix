package com.kuafoo4j.phoenix.client;

import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 服务本地缓存列表
 * @author: zk
 * @date: 2023-09-18 11:46
 */
public class ServiceHolder {
    /**
     * 本地注册表：
     * Map<String,ServiceInfo>
     * key：groupName@@serviceName@@clusters
     */
    Map<String, ServiceInfo> serviceInfoMap = new ConcurrentHashMap<>();


}
