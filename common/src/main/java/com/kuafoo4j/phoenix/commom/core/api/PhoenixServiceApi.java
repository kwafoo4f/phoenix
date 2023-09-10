package com.kuafoo4j.phoenix.commom.core.api;

import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 19:30
 */
public interface PhoenixServiceApi {
    /**
     * 注册服务
     * @return
     */
    ReturnResp registerService(RegisterReq registerReq);

    /**
     * 注销服务
     * @return
     */
    ReturnResp deRegisterService();

    /**
     * 服务心跳
     * @return
     */
    ReturnResp beat();

    /**
     * 获取所有实例
     *
     * @return
     */
    ReturnResp<ServiceInfo> getAllInstances(ServiceBaseReq req);

}
