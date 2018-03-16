package com.horrific;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wanghong12
 * @since 2018-3-12
 */

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Param {

    /**
     * 需要查询的字段
     */
    private String columns;

    /**
     * 过滤条件
     */
    private String whereOn;

    private String whereOr;

    /**
     * 聚合字段
     */
    private String groupBy;

    /**
     * 聚合条件
     */
    private String having;

    /**
     * 排序
     */
    private String orderBy;

    /**
     * 长度
     */
    private Integer size;

    /**
     * 偏移
     */
    private Integer offset;

    /**
     * 图表类型
     */
    private Integer chartType = -1;

    private String table;

    private String joinTable;
}
