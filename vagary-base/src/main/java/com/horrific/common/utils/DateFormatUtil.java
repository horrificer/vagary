package com.horrific.common.utils;


import com.horrific.common.enums.DatePatternEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程安全日期格式化工具
 * @author wanghong12
 * @date 2018-3-28
 */
public class DateFormatUtil {

    /**
     * 初始化ThreadLocal数据
     */
    private static ThreadLocal<Map<String,SimpleDateFormat>> sdfMap = new ThreadLocal<Map<String,SimpleDateFormat>>(){
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * 根据pattern(yyyy-MM-dd)获取simpleDateFormat
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getLocalSdf(String pattern) {
        Map<String, SimpleDateFormat> formatMap = sdfMap.get();
        SimpleDateFormat simpleDateFormat = formatMap.get(pattern);

        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            formatMap.put(pattern, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 根据pattern(yyyy-MM-dd)格式化日期数据
     * @param date
     * @param datePatternEnum
     * @return
     */
    public static String format(Date date, DatePatternEnum datePatternEnum) {
        return getLocalSdf(datePatternEnum.pattern).format(date);
    }

    /**
     * 根据pattern(yyyy-MM-dd)将String转为Date
     * @param dateStr
     * @param datePatternEnum
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr, DatePatternEnum datePatternEnum) throws ParseException {
        return getLocalSdf(datePatternEnum.pattern).parse(dateStr);
    }

}
