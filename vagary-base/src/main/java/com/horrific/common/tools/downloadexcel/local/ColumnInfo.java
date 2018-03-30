package com.horrific.common.tools.downloadexcel.local;

import com.horrific.common.enums.DataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列信息
 * <p>
 * Created by huyong on 2017/6/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {

    /**
     * 列位置
     */
    private int columnIndex;

    /**
     * 存储字段名字
     */
    private String columnName;

    /**
     * 列的默认值
     */
    private String columnDefaultValue = "";

    /**
     * 数据格式类型：NUMBER 、 DATE
     */
    private DataTypeEnum formatType;

    /**
     * 数据格式
     */
    private String formatPattern;

    public ColumnInfo(int columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    public ColumnInfo(int columnIndex, String columnName, String columnDefaultValue) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        this.columnDefaultValue = columnDefaultValue;
    }
}
