package com.horrific.common.utils;


import java.sql.Time;
import java.sql.Timestamp;

public final class DateUtil {

    /**
     * 将时间格式转成时间戳
     * @param o
     * @return
     */
    public static Object toValue(Object o){
        if (o instanceof java.util.Date){
            return ((java.util.Date) o).getTime();
        }else if (o instanceof java.sql.Date){
            return ((java.sql.Date) o).getTime();
        }else if (o instanceof Timestamp){
            return ((Timestamp) o).getTime();
        }else if(o instanceof Time){
            return ((Time) o).getTime();
        }else {
            return o;
        }
    }
}
