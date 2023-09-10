package com.kuafoo4f.phoenix.service;

import com.kuafoo4f.phoenix.pojo.InstanceModify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description:实例更新
 * @author: zk
 * @date: 2023-09-10 20:36
 */
@Slf4j
@Component
public class InstanceModifyService {
    @Autowired
    private ServiceManager serviceManager;
    /**
     * 阻塞队列
     */
    private static final BlockingQueue<InstanceModify> taskQueue = new LinkedBlockingQueue();

    private static final ExecutorService worker = Executors.newSingleThreadExecutor();

    public void run() {
        while (true) {
            try {
                InstanceModify modify = taskQueue.take();
                handle(modify);
            } catch (InterruptedException e) {
                log.error("实例更新到注册表失败", e);
            }
        }
    }

    private void handle(InstanceModify modify) {
        log.info("InstanceModifyService#handle-{}",modify);
    }

    /**
     * 添加任务
     * @param modify
     */
    public static void addTask(InstanceModify modify) {
        taskQueue.add(modify);
    }


    @PostConstruct
    public void init() {
        worker.execute(() -> run());
    }

}
