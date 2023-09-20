package com.kuafoo4j.phoenix.client;

import com.kuafoo4j.phoenix.commom.core.api.BeatReq;
import com.kuafoo4j.phoenix.commom.core.api.PhoenixServiceApi;
import com.kuafoo4j.phoenix.commom.core.api.RegisterReq;
import com.kuafoo4j.phoenix.commom.core.api.ReturnResp;
import com.kuafoo4j.phoenix.commom.core.constants.Constant;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import com.kuafoo4j.phoenix.config.ServiceProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 服务注册
 * @author: zk
 * @date: 2023-09-18 11:46
 */
@Slf4j
public class ServiceRegister {

    private PhoenixServiceApi apiProxy;

    private ServiceProperties serviceProperties;

    public ServiceRegister(PhoenixServiceApi apiProxy, ServiceProperties serviceProperties) {
        this.apiProxy = apiProxy;
        this.serviceProperties = serviceProperties;
        init();
    }

    public void init() {
        // 发起注册
        try {
            log.info("service register {} {}:{} to phoenix server",serviceProperties.getServiceName(),serviceProperties.getIp(),serviceProperties.getPort());
            RegisterReq registerReq = getRegisterReq();
            Instance instance = buildInstance();
            registerReq.setInstance(instance);
            ReturnResp returnResp = apiProxy.registerService(registerReq);
            log.info("service register to phoenix server result {}",returnResp);
        } catch (Exception e) {
            log.error("register to phoenix server err",e);
        }

        // 创建心跳任务
        ClientPoolManager.INSTANCE_BEAT_POOL.scheduleWithFixedDelay(this::beat,5,5, TimeUnit.SECONDS);
    }

    /**
     * 服务心跳
     */
    public void beat() {
        try {
            log.info("beat to phoenix server {} {}:{}",serviceProperties.getServiceName(),serviceProperties.getIp(),serviceProperties.getPort());
            BeatReq beatReq = getBeatReq();
            Instance instance = buildInstance();
            beatReq.setInstance(instance);
            ReturnResp returnResp = apiProxy.beat(beatReq);
            log.info("beat to phoenix server result {}",returnResp);
        } catch (Exception e) {
            log.error("beat to phoenix server err",e);
        }
    }

    private BeatReq getBeatReq() {
        BeatReq beatReq = new BeatReq();
        beatReq.setNamespaceId(Constant.NAMESPACE_DEFAULT);
        beatReq.setGroupName(Constant.GROUP_DEFAULT);
        beatReq.setClusterName(Constant.GROUP_DEFAULT);
        beatReq.setServiceName(serviceProperties.getServiceName());
        return beatReq;
    }

    private RegisterReq getRegisterReq() {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setNamespaceId(Constant.NAMESPACE_DEFAULT);
        registerReq.setGroupName(Constant.GROUP_DEFAULT);
        registerReq.setClusterName(Constant.GROUP_DEFAULT);
        registerReq.setServiceName(serviceProperties.getServiceName());
        return registerReq;
    }

    private Instance buildInstance() {
        Instance instance = new Instance();
        instance.setInstanceId(UUID.randomUUID().toString().replace("-",""));
        instance.setNamespaceId(Constant.NAMESPACE_DEFAULT);
        instance.setClusterName(Constant.GROUP_DEFAULT);
        instance.setServiceName(serviceProperties.getServiceName());
        instance.setClusterName(Constant.GROUP_DEFAULT);
        instance.setName(serviceProperties.getServiceName());
        instance.setIp(serviceProperties.getIp());
        instance.setPort(serviceProperties.getPort());
        return instance;
    }

}
