package com.horrific.common.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Point extends ChartDTO implements Serializable {
    private Object value;

    public Point(String name, Object value){
        super(name);
        this.value = value;
    }
}
