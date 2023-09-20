package com.kuafoo4j.phoenix.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @description: MapUtil
 * @author: zk
 * @date: 2023-01-12 13:52
 */
@Slf4j
public class MapUtil {

    /**
     * 对象转换为Map,字段名称为key,属性值为value
     *
     * @param obj
     * @return Map : key:fieldName,value:fieldValue
     */
    @SneakyThrows
    public static Map<String, String> pojo2Map(Object obj) {
        Map<String, String> map = new HashMap();
        if (Objects.isNull(obj)) {
            return map;
        }
        List<Field> fields = new ArrayList();
        // 从子类找到父类的所有字段-解决继承关系
        Class<?> clz = obj.getClass();
        while (clz != null) {
            Field[] declaredFields = clz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clz = clz.getSuperclass();
        }
        // 所有不为空的字段放入map中
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (Objects.nonNull(value)) {
                if (value instanceof Number) {
                    map.put(field.getName(), String.valueOf(value));
                } else if (value instanceof String) {
                    map.put(field.getName(), (String) value);
                } else if (value instanceof Boolean) {
                    map.put(field.getName(), String.valueOf(value));
                } else {
                    map.put(field.getName(), JacksonUtils.objectToJson(value));
                }
            }
        }
        return map;
    }

    /**
     * 对象转换为Map,字段名称为key,属性值为value
     *
     * @param obj
     * @return Map : key:fieldName,value:fieldValue
     */
    @SneakyThrows
    public static Map<String, Object> pojo2MapObj(Object obj) {
        Map<String, Object> map = new HashMap();
        if (Objects.isNull(obj)) {
            return map;
        }
        List<Field> fields = new ArrayList();
        // 从子类找到父类的所有字段-解决继承关系
        Class<?> clz = obj.getClass();
        while (clz != null) {
            Field[] declaredFields = clz.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            clz = clz.getSuperclass();
        }
        // 所有不为空的字段放入map中
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (Objects.nonNull(value)) {
                map.put(field.getName(), value);
            }
        }
        return map;
    }

}