package com.kuafoo4j.phoenix.config;

import cn.hutool.core.net.NetUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-18 14:08
 */
@Data
@Configuration
public class ServiceProperties {

    private String ip;

    @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String serviceName;

    @PostConstruct
    public void init() {
        // ip
        NetUtil.localIpv4s().stream()
                .filter(ipStr -> !"127.0.0.1".equals(ipStr))
                .findFirst().ifPresent((ip) -> {
                    this.ip = ip;
                });
    }

}
