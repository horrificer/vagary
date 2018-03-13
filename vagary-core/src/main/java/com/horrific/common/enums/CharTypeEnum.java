package com.horrific.common.enums;

/**
 * 【图形枚举类】
 *
 * @author wanghong12
 * @create 2017-08-22 16:50
 **/

public enum CharTypeEnum {
    TABLE(0, "表格"),
    LIST(1, "明细表"),
    LINE(10, "线图"),
    BAR(20, "柱状图"),
    STRIP(21, "条形图"),
    PIE(30, "饼图"),
    SELECT(4, "下拉"),
    TREE_MAP(5, "矩形树图"),
    SCATTER(60, "散点图"),
    RADAR(70, "雷达图"),
    GAUGE(80, "计量图"),
    INDEX_CARD(81, "指标卡"),
    MAP(90,"地图"),
    DEFAULT(0, "默认");

    public int code;
    public String desc;

    CharTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CharTypeEnum getDrawEnumByCode(Integer code) {
        if (code == null) {
            return LINE;
        }
        for (CharTypeEnum charTypeEnum : CharTypeEnum.values()) {
            if (code == charTypeEnum.code) {
                return charTypeEnum;
            }
        }
        return LINE;
    }
}
