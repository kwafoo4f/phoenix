package com.kuafoo4f.phoenix.controller;

import com.kuafoo4f.phoenix.service.ServiceManager;
import com.kuafoo4j.phoenix.commom.core.api.PhoenixServiceApi;
import com.kuafoo4j.phoenix.commom.core.api.RegisterReq;
import com.kuafoo4j.phoenix.commom.core.api.ReturnResp;
import com.kuafoo4j.phoenix.commom.core.api.ServiceBaseReq;
import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:54
 */
@RestController
@RequestMapping("/service/v1/")
public class ServiceController implements PhoenixServiceApi {

    @Autowired
    private ServiceManager serviceManager;

    @Override
    @PostMapping
    public ReturnResp registerService(@RequestBody RegisterReq registerReq) {
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
    public ReturnResp beat() {
        return null;
    }

    @Override
    @GetMapping
    public ReturnResp<ServiceInfo> getAllInstances(ServiceBaseReq req) {
        ServiceInfo serviceInfo = serviceManager.getInstances(req.getNamespaceId(), req.getGroupName(),
                req.getClusterName(), req.getServiceName());
        return ReturnResp.ok(serviceInfo);
    }
}
