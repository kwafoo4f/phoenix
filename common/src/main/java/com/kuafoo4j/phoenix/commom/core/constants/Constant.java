package com.kuafoo4j.phoenix.commom.core.constants;

import java.util.concurrent.TimeUnit;

/**
 * @description: 常量
 * @author: zk
 * @date: 2023-09-10 20:04
 */
public interface Constant {

    String DEFAULT = "default";

    String NAMESPACE_DEFAULT = DEFAULT;

    String GROUP_DEFAULT = DEFAULT;

    String SEPARATOR = "@@";

    String SEPARATOR_REGISTER = "::";
    /**
     * 服务健康时间15s
     */
    Long INSTANCE_UN_HEALTH_TIME = TimeUnit.SECONDS.toMillis(15);
    /**
     * 服务移除时间30s
     */
    Long INSTANCE_REMOVE_TIME = TimeUnit.SECONDS.toMillis(30);

}
