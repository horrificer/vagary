package com.horrific.common.utils;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jdk.nashorn.api.scripting.ScriptUtils.convert;


/**
 * 操作实体工具类
 * Created by shenhaonan on 2017/4/24.
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 类和该类所有字段的映射缓存map
     */
    private static final Map<String, List<Field>> fieldMapCache = new ConcurrentHashMap<>();

    /**
     * getClass方法
     */
    public static final String GETCLASS_METHOD = "getClass";

    /**
     * get方法的前缀
     */
    public static final String PREFIX_GET_METHOD = "get";

    /**
     * 从数据对象中获取指定fieldName对应的属性字段
     *
     * @param data      数据对象
     * @param fieldName 属性名
     * @param <T>
     * @return
     */
    public static <T> Field getField(T data, String fieldName) {

        for (Field field : getFields(data)) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取数据对象中所有属性字段
     *
     * @param data 数据对象
     * @param <T>
     * @return
     */
    public static <T> List<Field> getFields(T data) {

        Class<?> clazz = data.getClass();

        List<Field> fields = fieldMapCache.get(clazz.getCanonicalName());
        if (fields == null) {
            fields = ReflectUtils.getAllFieldsAsList(clazz);
            fieldMapCache.put(clazz.getCanonicalName(), fields);
        }

        return fields;

    }


    /**
     * 通过反射从数据中获取指定属性的值
     *
     * @param fieldName
     * @param data
     * @param <T>
     * @return
     */
    public static <T> String getProperty(String fieldName, T data) {

        try {
            return BeanUtils.getProperty(data, fieldName);

        } catch (Exception e) {
            try {
                Field field = getField(data, fieldName);
                field.setAccessible(true);
                return toString(field.get(data));
            } catch (Exception e2) {
                logger.error("使用反射获取变量-[{}]的值，报错了！{}", fieldName, e2);
            }
        }

        return "";

    }

    /**
     * 把一个对象的数据转换为Map结构
     *
     * @param data
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> convertObjectToMap(T data) {
        return convertObjectToMap(data, true);
    }

    public static <T> Map<String, Object> convertObjectToMap(T data, boolean isConvert) {

        Map<String, Object> map = Maps.newHashMap();

        if (data == null) {
            return map;
        }

        try {
            for (Field field : getFields(data)) {
                field.setAccessible(true);
                //把等于空对象和Map的数据过滤掉
                Object fieldValue = field.get(data);
                if (fieldValue != null
                        && !Map.class.isInstance(fieldValue)) {
                    if (isConvert) {
                        map.put(field.getName(), convert(fieldValue, BigDecimal.class));
                    } else {
                        map.put(field.getName(), fieldValue);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("java 简单对象转换为map结构异常！", e);
        }

        return map;

    }


    /**
     * clone一个对象
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> T cloneBean(T data) {

        try {
            Object clone = BeanUtils.cloneBean(data);
            return (T) clone;
        } catch (Exception e) {

        }

        return data;

    }

    public static String toString(Object data) {
        return data == null ? "" : data.toString();
    }

    /**
     * 通过反射创建实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }


    /**
     * 反射调用函数
     *
     * @param method
     * @param target
     * @param args
     * @return
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {

        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception e) {
            logger.error("method invoke error:{}", e.getMessage());
        }

        return null;

    }

    /**
     * 获取异常信息
     *
     * @param e
     * @return
     */
    public static String getErrMsg(Exception e) {
        StringBuilder sb = new StringBuilder("error message:").append(e.toString());
        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append(", cause:").append(cause.toString());
        }
        return sb.toString();
    }

}
