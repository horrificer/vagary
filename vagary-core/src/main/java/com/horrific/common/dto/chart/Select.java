package com.horrific.common.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by duanzhiying on 2017/10/22.
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Select extends ValueChartDTO {
    String label;

    public Select(String name, String value, String label) {
        super(name, value);
        this.label = label;
    }

    public Select(String value, String label){
        setValue(value);
        this.label = label;
    }
}
