package com.kuafoo4f.phoenix.service;

import com.kuafoo4f.phoenix.PoolManager;
import com.kuafoo4j.phoenix.commom.core.constants.Constant;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cluster extends com.kuafoo4j.phoenix.commom.core.pojo.Cluster {

    public Cluster(String name,Service service) {
        super.setName(name);
        super.setService(service);
        super.setServiceName(service.getName());
    }

    /**
     * 启动心跳检查和服务剔除任务,10秒一次
     */
    public void init() {
       PoolManager.INSTANCE_HEALTH_POOL.scheduleAtFixedRate(this::healthCheck,1,5, TimeUnit.SECONDS);
    }

    /**
     * 心跳检查和服务剔除任务
     */
    public void healthCheck() {
        Set<Instance> allInstances = getAllInstances();
        Set<Instance> removeSet = new HashSet<>();
        for (Instance instance : allInstances) {
            if (System.currentTimeMillis() - instance.getLastBeat() > Constant.INSTANCE_UN_HEALTH_TIME) {
                instance.setHealthy(false);
                log.info("insance {}:{} 已经不健康",instance.getIp(),instance.getPort());
            }
            if (System.currentTimeMillis() - instance.getLastBeat() > Constant.INSTANCE_REMOVE_TIME) {
                removeSet.add(instance);
                log.info("insance {}:{} 已经被剔除服务列表",instance.getIp(),instance.getPort());
            }
        }

        if (!CollectionUtils.isEmpty(removeSet)) {
            allInstances.removeAll(removeSet);
        }
        this.ephemeralInstances = allInstances;
    }

    /**
     * 集群下的实例
     */
    private Set<Instance> ephemeralInstances = new HashSet<Instance>();


    /**
     * 获取所有实例
     * @return
     */
    public Set<Instance> getAllInstances() {
        return new HashSet<>(ephemeralInstances);
    }

    /**
     * 获取实例
     *
     * @param ip
     * @param port
     * @return
     */
    public Instance getInstance(String ip ,Integer port) {
        for (Instance instance : ephemeralInstances) {
            if (instance.getIp().equals(ip) && instance.getPort() == port) {
                return instance;
            }
        }
        return null;
    }

    /**
     * 添加实例
     * @param instance
     */
    public void addInstance(Instance instance) {
        ephemeralInstances.add(instance);
    }
}
