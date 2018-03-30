package com.horrific.common.utils;

import java.math.BigDecimal;

public class NumberUtil {


    /**
     * 判断是否为数字
     *
     * @param data 数据
     * @return
     */
    public static boolean isNumberOfValue(Object data) {

        if (data == null) {
            return false;
        }

        Class<?> fieldType = data.getClass();

        //如果是包装类型或基本类型
        return Number.class.isAssignableFrom(fieldType)
                || fieldType == short.class
                || fieldType == int.class
                || fieldType == float.class
                || fieldType == double.class
                || fieldType == long.class;

    }

    /**
     * 判断是否为字符串类型的数字
     *
     * @param data 数据
     * @return
     */
    public static boolean isStringNumber(Object data) {

        if (data == null || !(data instanceof String)) {
            return false;
        }
        try{
           new BigDecimal(data.toString());
           return true;
        }catch (Exception e){
            return false;
        }
    }



    /**
     * 两个数相除，如果存在不是数字，或除法异常，返回默认值
     *
     * @param dataA        被除数
     * @param dataB        除数
     * @param defaultValue 默认值
     * @return
     */
    public static Object divide(Object dataA, Object dataB, Object defaultValue) {
        return divide(dataA, dataB, 20, defaultValue);
    }

    /**
     * 两个数相除，如果存在不是数字，或除法异常，返回默认值
     *
     * @param dataA 被除数
     * @param dataB 除数
     * @return
     */

    /**
     * 把数据转换为BigDecimal类型
     *
     * @param data
     * @return
     */
    public static BigDecimal toBigDecimal(Object data) {
        if (isNumber(data)) {
            return new BigDecimal(data.toString());
        }
        return BigDecimal.ZERO;
    }

    /**
     * 两个数相除，如果存在不是数字，或除法异常，返回默认值
     *
     * @param dataA        被除数
     * @param dataB        除数
     * @param scale        精度
     * @param defaultValue 默认值
     * @return
     */
    public static Object divide(Object dataA, Object dataB, int scale, Object defaultValue) {

        if (isNumber(dataA) && isNumber(dataB)) {

            try {
                //注意循环小数的出现，因此设置保留小数位数，四舍五入的方式
                return toBigDecimal(dataA).divide(toBigDecimal(dataB), scale, BigDecimal.ROUND_HALF_UP).toString();
            } catch (Exception e) {
                return defaultValue;
            }

        }

        return defaultValue;

    }

    /**
     * 判断是否为数字
     *
     * @param data
     * @return
     */
    public static boolean isNumber(Object data) {
        return isNumberOfValue(data) || isStringNumber(data);
    }

    /**
     * 科学计数法转化
     *
     * @param value
     * @return
     */
    public static Object fetchConvertScientificNotationNum(Object value){
        if(FormatUtil.isScientificNotation(value.toString())){
            return new BigDecimal(value.toString()).toPlainString();
        }
        return value;
    }

    /**
     * 处理NaN数据
     *
     * @param value
     * @return
     */
    public static Object fetchConvertNaN(Object value){
        if(isNaN(value)){
            return 0;
        }
        return value;
    }

    /**
     * 判断是否是NaN
     *
     * @param value
     * @return
     */
    public static boolean isNaN(Object value){
        String strvalue = value.toString();
        return  Float.isNaN(Float.valueOf(strvalue))
                || Double.isNaN(Double.valueOf(strvalue));
    }
}
