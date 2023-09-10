package com.kuafoo4f.phoenix.pojo;

import com.kuafoo4j.phoenix.commom.core.pojo.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-10 20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceModify {
    public static final String MODIFY_CHANGE = "ADD",
            MODIFY_REMOVE = "REMOVE", MODIFY_UPDATE = "UPDATE";

    /**
     * 变更类型
     */
    private String modifyMode;

    /**
     * 实例
     */
    private Instance instance;

}
