package com.kuafoo4f.phoenix;

import java.util.concurrent.*;

/**
 * 线程池管理
 */
public class PoolManager {

    /**
     * 服务健康检查和服务剔除
     */
    public static final ScheduledThreadPoolExecutor INSTANCE_HEALTH_POOL =  new ScheduledThreadPoolExecutor(1);

}
