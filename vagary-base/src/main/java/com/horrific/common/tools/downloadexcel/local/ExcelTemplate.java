package com.horrific.common.tools.downloadexcel.local;

import lombok.Data;

import java.util.Map;

/**
 * excel模板
 * <p>
 * @author wanghong12
 * @date 2018-3-28
 */
@Data
public class ExcelTemplate {

    /**
     * Excel模板名字
     */
    private String templateName;

    /**
     * 生成的excel的名字
     */
    private String excelName;

    /**
     * Excel 数据体描述信息
     */
    private Map<String, ColumnInfo> bodyInfo;

    /**
     * Excel 数据体开始的位置
     */
    private int bodyStartIndex;

}
