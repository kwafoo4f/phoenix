package com.kuafoo4j.phoenix.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-20 15:11
 */
@Data
@Configuration
public class DiscoveryProperties {

    @Value("${phoenix.discovery.host}")
    private String discoveryHost;

}
