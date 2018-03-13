package com.horrific.common.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by duanzhiying on 2017/10/17.
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RadarDimensionDTO extends ChartDTO {
    public Double max;

    public RadarDimensionDTO(String name,Double max){
        super(name);
        this.max = max;
    }

    public RadarDimensionDTO(String name){
        super(name);
    }
}
