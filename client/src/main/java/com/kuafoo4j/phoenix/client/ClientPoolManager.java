package com.kuafoo4j.phoenix.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * 线程池管理
 */
public class ClientPoolManager {

    /**
     * 服务心跳
     */
    public static final ScheduledExecutorService INSTANCE_BEAT_POOL =  new ScheduledThreadPoolExecutor(1,new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("phoenix.client.beat");
            return thread;
        }
    });

    /**
     * 服务拉取
     */
    public static final ScheduledExecutorService SERVICE_UPDATE_POOL =  new ScheduledThreadPoolExecutor(2,new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("phoenix.client.service.updater");
            return thread;
        }
    });

}
