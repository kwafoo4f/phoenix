package com.kuafoo4f.phoenix.service;

import com.kuafoo4f.phoenix.pojo.InstanceModify;
import com.kuafoo4j.phoenix.commom.core.api.BeatReq;
import com.kuafoo4j.phoenix.commom.core.api.RegisterReq;
import com.kuafoo4j.phoenix.commom.core.api.ReturnResp;
import com.kuafoo4j.phoenix.commom.core.constants.Constant;
import com.kuafoo4j.phoenix.commom.core.exception.ExceptionBiz;
import com.kuafoo4j.phoenix.commom.core.exception.PhoenixException;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:58
 */
@Slf4j
@Component
public class ServiceManager {
    /**
     * 注册表:Map(namespace,Map(group::serviceName,Service))
     */
    private Map<String,Map<String, Service>> serviceMap = new ConcurrentHashMap<>();


    public void registerInstance(RegisterReq registerReq) {
        // 创建空的Service
        createEmptyService(registerReq.getNamespaceId(),registerReq.getGroupName(),
                registerReq.getServiceName());
        Instance instance = registerReq.getInstance();
        // 添加实例
        addInstance(instance);
    }

    public void addInstance(Instance instance) {
//        // 向队列中加入添加实例的任务，TODO 异步处理，写时复制
//        InstanceModify modify = new InstanceModify(InstanceModify.MODIFY_CHANGE, instance);
//        InstanceModifyService.addTask(modify);


        // 直接加入到service中
        Service service = getService(instance.getNamespaceId(), instance.getServiceName());
        log.debug("add instance to {}",service.getName());
        service.addInstance(instance);

    }

    /**
     * 创建空Service,主要是填充注册表的结构
     * @param namespaceId
     * @param groupName
     * @param serviceName
     * @return
     */
    private Service createEmptyService(String namespaceId, String groupName, String serviceName) {
        Service service = getService(namespaceId,serviceName);
        if (service == null) {
            service = new Service();
            service.setNamespaceId(namespaceId);
            service.setName(serviceName);
            service.setGroupName(groupName);
            putServiceAndInit(namespaceId, service);
        }
        return service;
    }

    private void putServiceAndInit(String namespaceId, Service service) {
        putService(namespaceId,service);
    }

    private void putService(String namespaceId, Service service) {
        if (!serviceMap.containsKey(namespaceId)) {
            serviceMap.put(namespaceId,new ConcurrentHashMap<>());
        }
        serviceMap.get(namespaceId).put(service.getName(), service);
    }

    private Service getService(String namespaceId,String serviceName) {
        Map<String, Service> serviceMap = chooseMap(namespaceId);
        if (serviceMap == null) {
            return null;
        }

        return serviceMap.get(serviceName);
    }

    private Map<String, Service> chooseMap(String namespaceId) {
        return serviceMap.get(namespaceId);
    }

    public ServiceInfo getInstances(String namespaceId, String groupName, String serviceName, String clusterName) {
        Service service = getService(namespaceId, serviceName);
        if (service == null) {
            throw new PhoenixException(ExceptionBiz.NO_SUCH_SERVICE);
        }
        List<Instance> allInstances = service.getAllInstances(clusterName);

        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName(service.getName());
        serviceInfo.setGroupName(groupName);
        serviceInfo.setClusters(clusterName);
        serviceInfo.setHosts(allInstances);
        return serviceInfo;
    }

    /**
     * 心跳处理
     *
     * @param beatReq
     */
    public void beat(BeatReq beatReq) {
        Service service = getService(beatReq.getNamespaceId(), beatReq.getServiceName());
        if (service == null) {
            throw new PhoenixException(ExceptionBiz.NO_SUCH_SERVICE);
        }
        service.beat(beatReq.getInstance());

    }
}
