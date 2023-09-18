package com.kuafoo4j.phoenix.client;

import com.kuafoo4j.phoenix.commom.core.api.PhoenixServiceApi;

/**
 * @description: 服务注册
 * @author: zk
 * @date: 2023-09-18 11:46
 */
public class ServiceRegister {

    private PhoenixServiceApi apiProxy;

    private ServiceHolder serviceHolder;

    public ServiceRegister(PhoenixServiceApi apiProxy, ServiceHolder serviceHolder) {
        this.apiProxy = apiProxy;
        this.serviceHolder = serviceHolder;
        init();
    }

    public void init() {



    }

}
