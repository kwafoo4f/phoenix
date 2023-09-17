package com.kuafoo4f.phoenix.service;

import com.kuafoo4j.phoenix.commom.core.pojo.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务
 */
public class Service extends com.kuafoo4j.phoenix.commom.core.pojo.Service {
    /**
     * 集群， Map<Cluster::getName,Cluster>
     */
    private Map<String, Cluster> clusterMap = new ConcurrentHashMap<>();

    /**
     * 获取所有实例
     *
     * @param clusterName
     * @return
     */
    public List<Instance> getAllInstances(String clusterName) {
        Cluster cluster = clusterMap.get(clusterName);
        if (cluster == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(cluster.getAllInstances());
    }


    /**
     * 添加实例
     *
     * @param instance
     */
    public void addInstance(Instance instance) {
        Cluster cluster = clusterMap.get(instance.getClusterName());
        if (cluster == null) {
            cluster = new Cluster(instance.getClusterName(),this);
            cluster.init();
            clusterMap.put(instance.getClusterName(),cluster);
        }
        cluster.addInstance(instance);
    }

    public void beat(Instance instanceReq) {
        Cluster cluster = clusterMap.get(instanceReq.getClusterName());

        Instance instance = cluster.getInstance(instanceReq.getIp(), instanceReq.getPort());
        if (instance == null) {
            addInstance(instanceReq);
        } else {
            instance.updateBeat();
        }
    }
}
