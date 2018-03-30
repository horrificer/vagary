package com.horrific.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaojun12 on 2017/3/26.
 */
public class ReflectUtils {

    public static Field[] getAllFields(Class<?> targetClz) {
        return getAllFieldsAsList(targetClz).toArray(new Field[]{});
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    public static List<Field> getAllFieldHasAnno(Class<?> clazz, Class anno) {

        List<Field> result = new ArrayList<>();

        List<Field> fields = getAllFieldsAsList(clazz);
        for (Field field : fields) {
            if (field.getAnnotation(anno) != null) {
                result.add(field);
            }
        }

        return result;

    }

    public static List<Field> getAllFieldsAsList(Class<?> targetClz) {
        List<Field> fields = Lists.newArrayList();
        Set<String> nameSet = Sets.newHashSet();

        while (targetClz != null) {
            for (Field field : targetClz.getDeclaredFields()) {
                if (nameSet.contains(field.getName()) || Modifier.isFinal(field.getModifiers())) continue;
                nameSet.add(field.getName());
                fields.add(field);
            }

            targetClz = targetClz.getSuperclass();
        }
        return fields;
    }
}
