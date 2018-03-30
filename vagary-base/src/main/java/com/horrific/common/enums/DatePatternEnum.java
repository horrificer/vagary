package com.horrific.common.enums;

public enum DatePatternEnum {
    DATE_FORMAT("yyyy-MM-dd"),
    DATE_FORMAT_SHORT("yyyyMMdd"),
    MONTH_FORMAT("MM-dd"),
    DATE_FORMAT_24H("yyyy-MM-dd HH:mm:ss"),
    DATE_FORMAT_12H("yyyy-MM-dd hh:mm:ss"),
    DATE_FORMAT_WEEK("yyyy-ww"),
    DATE_FORMAT_MONTH("yyyy-MM"),
    DATE_FORMAT_00H("yyyy-MM-dd 00:00:00"),
    DATE_FORMAT_23H("yyyy-MM-dd 23:59:59"),
    DATE_FORMAT_CHIAN("yyyy年MM月dd日HH点");

    public String pattern;

    private DatePatternEnum(String pattern) {
        this.pattern = pattern;
    }
}
