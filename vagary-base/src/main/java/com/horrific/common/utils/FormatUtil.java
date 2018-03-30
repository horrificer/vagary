package com.horrific.common.utils;


import com.horrific.common.enums.DataTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.horrific.common.utils.NumberUtil.*;


/**
 * 格式化工具类
 * author:huyong
 * date:2017/4/26
 * time:12:23
 */
public class FormatUtil {

    /**
     * 科学计数法的表示符号
     */
    private final static String SCIENTIFIC_NOTATION_SYMBOL = "E";

    /**
     * 对数值类型的数据进行格式化
     *
     * @param data    待格式化的数据
     * @param pattern 格式化模型
     * @param locale  本地化
     * @return
     */
    public static String formatNumber(Object data, String pattern, Locale locale) {

        boolean isNum = NumberUtil.isNumberOfValue(data) || NumberUtil.isStringNumber(data);

        if (!isNum) {
            return data == null ? "" : data.toString();
        }

        if (data instanceof String) {
            data = new BigDecimal((String) data);
        }

        if (isScientificNotation(pattern)) {
            return formatNumberScientificNotation(data, pattern, locale);

        } else {
            return formatNumberCommon(data, pattern, locale);
        }

    }

    /**
     * 日期格式化
     *
     * @param data    带格式化的日期
     * @param pattern 日期模板
     * @param locale  本地化
     * @return
     */
    public static String formatDate(Object data, String pattern, Locale locale) {

        if (String.class.isInstance(data)
                && isNumber(data)) {
            data = Long.parseLong((String) data);
        }

        //只有日期类型或数字类型才允许格式化
        boolean allowType = (data instanceof Date) || (data instanceof Number);

        if (allowType == false) {
            return (String) data;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(data);

    }


    /**
     * 对数据使用pattern进行格式化
     *
     * @param dataType 待格式化数据的数据类型
     * @param data     待格式化的数据
     * @param pattern  格式化之后的模式：如：#,###.00% 、yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String formatByDataType(DataTypeEnum dataType, Object data, String pattern) {
        return formatByDataType(dataType, data, pattern, Locale.getDefault());
    }

    /**
     * 对数据使用pattern进行格式化
     *
     * @param dataType 待格式化数据的数据类型
     * @param data     待格式化的数据
     * @param pattern  格式化之后的模式：如：#,###.00% 、yyyy-MM-dd HH:mm:ss.SSS
     * @param locale   本地化
     * @return
     */
    public static String formatByDataType(DataTypeEnum dataType, Object data, String pattern, Locale locale) {

        if (pattern != null) {

            switch (dataType) {

                case NUMBER://对数字使用模板进行格式化
                    return formatNumber(data, pattern, locale);

                case DATE://对日期使用模板进行格式化
                    return formatDate(data, pattern, locale);

            }

        }

        return data == null ? "" : data.toString();

    }


    /**
     * 普通数字格式化(非科学计数法)
     *
     * @param data    待格式化的数据
     * @param pattern 格式化模型
     * @param locale  本地化
     * @return
     */
    private static String formatNumberCommon(Object data, String pattern, Locale locale) {

        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(locale);

        format.setRoundingMode(RoundingMode.HALF_UP);
        format.applyPattern(pattern);

        return format.format(data);

    }

    /**
     * 使用科学计数法的模板进行格式化(对JDK的科学计数法进行了扩展)
     *
     * @param data    待格式化的数据
     * @param pattern 格式化模型
     * @param locale  本地化
     * @return
     */
    private static String formatNumberScientificNotation(Object data, String pattern, Locale locale) {

        //按科学计数法的方式进行除法运算
        int scientificNotationNum = fetchScientificNotationNum(pattern);
        data = toBigDecimal(divide(data, Math.pow(10, scientificNotationNum),""));

        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(locale);

        format.setRoundingMode(RoundingMode.HALF_UP);
        format.applyPattern(assemblePattern(pattern));

        if (scientificNotationNum == 0) {//表示E0
            return format.format(data);

        } else {
            return new StringBuilder(format.format(data)).append(SCIENTIFIC_NOTATION_SYMBOL).append(scientificNotationNum).toString();
        }

    }

    /**
     * 对科学计数法做处理,科学计数法，会使用‘E’来表示，如#0.00E0
     *
     * @param pattern 格式化模板
     * @return
     */
    private static String assemblePattern(String pattern) {

        //E字符后面的数据：如0E3，这里的数字就是3
        int num = fetchScientificNotationNum(pattern);

        switch (num) {

            case 0://相当于E0,JDK 原生支持的科学计数法
                return pattern;

            default://比如#.00E3，其中E3，JDK不支持，直接返回#.00
                return pattern.substring(0, fetchScientificNotationIndex(pattern));

        }

    }

    /**
     * 获取科学计数法E所在的位置
     *
     * @param pattern 格式化模板
     * @return
     */
    private static int fetchScientificNotationIndex(String pattern) {
        return pattern.indexOf(SCIENTIFIC_NOTATION_SYMBOL);
    }

    /**
     * 判断数字模型是否含有科学计数法
     *
     * @param pattern 格式化模板
     * @return
     */
    public static boolean isScientificNotation(String pattern) {
        return fetchScientificNotationIndex(pattern) > -1;
    }

    /**
     * 获取科学计数法后面的数字，如#0.00E4， 获取得到结果为：4
     *
     * @param pattern 格式化模板
     * @return
     */
    private static int fetchScientificNotationNum(String pattern) {

        int index = fetchScientificNotationIndex(pattern);

        if (index == -1) {
            return 0;
        }

        return Integer.parseInt(pattern.substring(index + 1));

    }

}
