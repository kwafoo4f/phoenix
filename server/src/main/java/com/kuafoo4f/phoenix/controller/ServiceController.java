package com.kuafoo4f.phoenix.controller;

import com.kuafoo4f.phoenix.service.ServiceManager;
import com.kuafoo4j.phoenix.commom.core.api.*;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:54
 */
@Slf4j
@RestController
@RequestMapping("/service/v1/")
public class ServiceController implements PhoenixServiceApi {

    @Autowired
    private ServiceManager serviceManager;

    @Override
    @PostMapping
    public ReturnResp registerService(@RequestBody RegisterReq registerReq) {
        log.info("register serviceName {},{}:{}",registerReq.getServiceName(),
                registerReq.getInstance().getIp(),registerReq.getInstance().getPort());
        serviceManager.registerInstance(registerReq);
        return ReturnResp.ok();
    }

    @Override
    @DeleteMapping
    public ReturnResp deRegisterService() {
        return null;
    }

    @Override
    @PostMapping("/beat")
    public ReturnResp beat(@RequestBody BeatReq beatReq) {
        log.info("beat serviceName {},{}:{}",beatReq.getServiceName(),
                beatReq.getInstance().getIp(),beatReq.getInstance().getPort());
        serviceManager.beat(beatReq);
        return ReturnResp.ok();
    }

    @Override
    @GetMapping
    public ReturnResp<ServiceInfo> getAllInstances(ServiceBaseReq req) {
        ServiceInfo serviceInfo = serviceManager.getInstances(req.getNamespaceId(), req.getGroupName(),
                req.getServiceName(), req.getClusterName());
        return ReturnResp.ok(serviceInfo);
    }
}
