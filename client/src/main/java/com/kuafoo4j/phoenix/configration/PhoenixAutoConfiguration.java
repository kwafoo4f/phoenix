package com.kuafoo4j.phoenix.configration;

import com.kuafoo4f.phoenix.rpc.PhoenixHttpClient;
import com.kuafoo4j.phoenix.client.ServiceRegister;
import com.kuafoo4j.phoenix.commom.core.api.PhoenixServiceApi;
import com.kuafoo4j.phoenix.config.DiscoveryProperties;
import com.kuafoo4j.phoenix.config.ServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自动装配
 * @author: zk
 * @date: 2023-09-20 15:06
 */
@Configuration
public class PhoenixAutoConfiguration {

    @Bean
    public PhoenixServiceApi phoenixServiceApi(DiscoveryProperties discoveryProperties) {
        return new PhoenixHttpClient(discoveryProperties.getDiscoveryHost());
    }

    @Bean
    public ServiceRegister serviceProperties(PhoenixServiceApi apiProxy, ServiceProperties serviceProperties) {
        return new ServiceRegister(apiProxy,serviceProperties);
    }

}
