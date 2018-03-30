package com.horrific.common.utils;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hongshuai1 on 2017/2/10.
 */
public final class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static String[] toArray(List<String> values) {
        String[] valuesAsArray = new String[values.size()];
        return values.toArray(valuesAsArray);
    }

    /**
     * 把List转换Map结构，Map值的为V类型的POJO
     *
     * @param datas      list集合的数据
     * @param joiner     key的拼接符号
     * @param fieldNames 单条数据中哪些字段的值作为key
     * @param <V>        List中单条数据的数据类型
     * @return
     */
    public static <V> Map<String, V> listToMap(List<V> datas, String joiner, String... fieldNames) {

        Map<String, V> map = Maps.newHashMap();

        datas.forEach(data -> {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < fieldNames.length; i++) {
                sb.append(BeanUtil.getProperty(fieldNames[i], data));
                if (i != fieldNames.length - 1) {
                    sb.append(joiner);
                }
            }
            map.put(sb.toString(), data);

        });

        return map;

    }


    /**
     * 对集合按指定的字段进行排序
     *
     * @param datas         待排序的集合
     * @param sortFieldName 排序时，使用的比较字段的字段名
     * @param asc           true-升序 false-降序
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> datas, final String sortFieldName, boolean asc) {
        datas = datas.stream()
                .sorted((o1, o2) -> {
                    String first = first = BeanUtil.getProperty(sortFieldName, o1);
                    String second = second = BeanUtil.getProperty(sortFieldName, o2);
                    if (asc) {
                        return first.compareTo(second);
                    } else {
                        return second.compareTo(first);
                    }
                }).collect(Collectors.toList());
        return datas;
    }

    /**
     * 类型转换
     *
     * @param subs
     * @param parents
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T extends V, V> List<V> convertType(List<T> subs, List<V> parents) {
        if (subs == null) {
            return null;
        }
        subs.stream().forEach(sub -> {
            parents.add(sub);
        });
        return parents;
    }
}
