package com.kufoo4j.service;

import com.kuafoo4j.phoenix.client.ServiceHolder;
import com.kuafoo4j.phoenix.commom.core.api.ReturnResp;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import com.kuafoo4j.phoenix.config.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 获取服务测试
 * @author: zk
 * @date: 2023-09-20 17:32
 */
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceHolder serviceHolder;
    @Autowired
    private ServiceProperties serviceProperties;

    @GetMapping
    public ReturnResp getServiceInfo(@RequestParam String serviceName) {
        ServiceInfo serviceInfo = serviceHolder.getAllInstance(serviceName);
        return ReturnResp.ok(serviceInfo);
    }

    @GetMapping("/server-info")
    public String getServerInfo() {
        StringBuffer serverInfo = new StringBuffer()
                .append(serviceProperties.getServiceName()).append("@")
                .append(serviceProperties.getIp())
                .append(":").append(serviceProperties.getPort());
        return serverInfo.toString();
    }

    @GetMapping("/all")
    public ReturnResp getAllService() {
        return ReturnResp.ok(serviceHolder.getAllService());
    }

}
