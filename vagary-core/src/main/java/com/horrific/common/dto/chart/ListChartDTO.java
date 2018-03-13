package com.horrific.common.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by duanzhiying on 2017/10/15.
 * 图表转换DTO：value类型是list
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ListChartDTO<T> extends ChartDTO {
    /**
     * 具体的值
     */
    private List<T> data;

    public ListChartDTO(String name, List<T> data){
        super(name);
        this.data = data;
    }
}
