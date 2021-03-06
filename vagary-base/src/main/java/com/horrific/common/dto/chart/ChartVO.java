package com.horrific.common.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by duanzhiying on 2017/10/15.
 * 图表转换VO的基本属性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChartVO implements Serializable {
    /**
     * 图表类型，见:{@link: com.jd.jdp.common.enums.DrawEnum}
     */
    private Integer chartType;
}
