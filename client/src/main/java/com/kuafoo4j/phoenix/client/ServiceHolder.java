package com.kuafoo4j.phoenix.client;

import com.kuafoo4j.phoenix.commom.core.api.IPhoenixServerDiscovery;
import com.kuafoo4j.phoenix.commom.core.api.PhoenixServiceApi;
import com.kuafoo4j.phoenix.commom.core.api.ReturnResp;
import com.kuafoo4j.phoenix.commom.core.api.ServiceBaseReq;
import com.kuafoo4j.phoenix.commom.core.constants.Constant;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @description: 服务本地缓存列表
 * @author: zk
 * @date: 2023-09-18 11:46
 */
@Slf4j
public class ServiceHolder implements IPhoenixServerDiscovery {

    private static final Map<String, UpdateTask> serviceUpdateTaskMap = new ConcurrentHashMap<>();

    private PhoenixServiceApi apiProxy;

    public ServiceHolder(PhoenixServiceApi apiProxy) {
        this.apiProxy = apiProxy;
    }

    /**
     * 本地注册表：
     * Map<String,ServiceInfo>
     * key：groupName@@serviceName@@clusters
     */
    Map<String, ServiceInfo> serviceInfoMap = new ConcurrentHashMap<>();

    /**
     * 获取所服务
     *
     * @return
     */
    public Map<String, ServiceInfo> getAllService() {
        return serviceInfoMap;
    }

    /**
     * 获取服务列表
     *
     * @param serviceName
     * @return
     */
    public ServiceInfo getAllInstance(String serviceName) {
        return getCacheAllInstance(serviceName);
    }

    /**
     * 从本地获取服务列表
     *
     * @param serviceName
     * @return
     */
    public ServiceInfo getCacheAllInstance(String serviceName) {
        ServiceInfo emptyServiceInfo = getEmptyServiceInfo(serviceName);
        ServiceInfo serviceInfo = serviceInfoMap.get(emptyServiceInfo.getKey());
        if (serviceInfo != null) {
            return serviceInfo;
        }

        // 本地获取不到就去远程获取
        return getDiscoveryAllInstance(serviceName);
    }

    /**
     * 从注册中心获取服务列表
     *
     * @param serviceName
     * @return
     */
    private ServiceInfo getDiscoveryAllInstance(String serviceName) {
        ServiceBaseReq baseReq = getBaseReq(serviceName);
        log.info("从注册中心获取服务列表 serviceName {} ",serviceName);
        ReturnResp<ServiceInfo> returnResp = apiProxy.getAllInstances(baseReq);
        log.info("从注册中心获取服务列表 returnResp {} ",returnResp);
        ServiceInfo serviceInfo = returnResp.getDate();
        if (serviceInfo != null) {
            // 放入到本地缓存
            serviceInfoMap.put(serviceInfo.getKey(), serviceInfo);
            // 开启定时任务定时拉取
            addUpdateTaskIfAbsent(serviceName);
        }
        return serviceInfo;
    }

    /**
     * 开启定时任务定时拉取
     *
     * @param serviceName
     */
    private void addUpdateTaskIfAbsent(String serviceName) {
        synchronized (serviceUpdateTaskMap) {
            if (serviceUpdateTaskMap.get(serviceName) != null) {
                return;
            }
            ClientPoolManager.SERVICE_UPDATE_POOL.schedule(new UpdateTask(serviceName), 5, TimeUnit.SECONDS);
            serviceUpdateTaskMap.put(serviceName, new UpdateTask(serviceName));
        }
    }


    private ServiceBaseReq getBaseReq(String serviceName) {
        ServiceBaseReq baseReq = new ServiceBaseReq();
        baseReq.setNamespaceId(Constant.NAMESPACE_DEFAULT);
        baseReq.setGroupName(Constant.GROUP_DEFAULT);
        baseReq.setClusterName(Constant.DEFAULT);
        baseReq.setServiceName(serviceName);
        return baseReq;
    }

    private ServiceInfo getEmptyServiceInfo(String serviceName) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName(serviceName);
        serviceInfo.setGroupName(Constant.GROUP_DEFAULT);
        serviceInfo.setClusters(Constant.DEFAULT);
        serviceInfo.setHosts(Collections.EMPTY_LIST);
        return serviceInfo;
    }

    @Override
    public List<Instance> getInstanceList(String serviceName) {
        ServiceInfo serviceInfo = getAllInstance(serviceName);
        if (serviceInfo == null) {
            return null;
        }

        return new ArrayList<>(serviceInfo.getHosts());
    }

    @Data
    public class UpdateTask implements Runnable {
        private String serviceName;
        private Long failCount = 0L;

        public UpdateTask(String serviceName) {
            this.serviceName = serviceName;
        }

        @Override
        public void run() {
            try {
                // 从远端拉取服务更新本地注册表
                log.info("定时从远端拉取服务更新本地注册表 serviceName {} ",serviceName);
                getDiscoveryAllInstance(serviceName);
            } catch (Exception e) {
                log.error("拉取服务 {} 失败,失败次数 {} ", serviceName, failCount, e);
                failCount++;
            } finally {
                // 5s后继续拉取
                ClientPoolManager.SERVICE_UPDATE_POOL.schedule(this, 5, TimeUnit.SECONDS);
            }
        }
    }


}
