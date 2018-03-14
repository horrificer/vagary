package com.horrific.common.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author duanzhiying
 * @version 创建时间：2017/10/15
 * 图表转换的默认VO：可覆盖TendencyVO/TreeMapVO
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class DefaultChartVO<E,T> extends ChartVO{
    /**
     * 维度列表
     */
    private List<E> dimension;
    /**
     * 度量列表
     */
    private List<T> measure;

    /**
     * 构造函数
     * @param chartType 图表类型
     * @param dimension 维度列表
     * @param measure 度量列表
     */
    public DefaultChartVO(Integer chartType, List<E> dimension, List<T> measure){
        super(chartType);
        this.dimension = dimension;
        this.measure = measure;
    }

}
