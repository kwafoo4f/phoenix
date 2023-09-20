package com.kuafoo4j.phoenix.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池管理
 */
public class ClientPoolManager {

    /**
     * 服务心跳
     */
    public static final ScheduledExecutorService INSTANCE_BEAT_POOL =  new ScheduledThreadPoolExecutor(1);

}
