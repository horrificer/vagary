package com.horrific.common.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by duanzhiying on 2017/10/15.
 * 图表转换DTO：value类型是一个值
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ValueChartDTO extends ChartDTO {
    /**
     * 具体的值，通用返回String
     */
    private String value;

    public ValueChartDTO(String legend, String value){
        super(legend);
        this.value = value;
    }
}
