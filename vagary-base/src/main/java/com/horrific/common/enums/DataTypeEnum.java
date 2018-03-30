package com.horrific.common.enums;

/**
 * @author wanghong12
 * @date 2018-3-28
 */
public enum DataTypeEnum {
    THOUSANDS("THOUSANDS", "千分位"),
    THOUSANDSZERO("THOUSANDSZERO", "千分位,取整"),
    THOUSANDSZEROTWO("THOUSANDSZEROTWO", "千分位保留两位小数"),
    THOUSANDSZEROTHREE("THOUSANDSZEROTHREE", "千分位保留三位小数"),
    THOUSANDSZEROFOUR("THOUSANDSZEROFOUR", "千分位保留四位小数"),
    INTEGER("INTEGER", "整数"),
    PERCENT("PERCENT", "百分数,保留两位小数"),
    PERCENTONE("PERCENTONE", "百分数,保留一位小数"),
    PERCENTZERO("PERCENTZERO", "百分数,取整"),
    SCALETOTWO("SCALETOTWO", "保留两位有效数字"),
    SCALETOONE("SCALETOONE", "保留一位有效数字"),
    SCALETOTHREE("SCALETOTHIRD", "保留三位有效数字"),
    SCALETOFOUR("SCALETOFOUR", "保留四位有效数字"),
    NUMBER("NUMBER", "数字"),
    DATE("DATE", "日期");

    public String desc;
    public String code;

    private DataTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String toString() {
        return "DataTypeEnum{desc='" + this.desc + '\'' + ", code='" + this.code + '\'' + '}';
    }
}