package com.kuafoo4f.phoenix.service;

import java.util.concurrent.*;

/**
 * 线程池管理
 */
public class PoolManager {

    /**
     * 服务健康检查和服务剔除
     */
    public static final ScheduledExecutorService INSTANCE_HEALTH_POOL =  new ScheduledThreadPoolExecutor(1);

}
