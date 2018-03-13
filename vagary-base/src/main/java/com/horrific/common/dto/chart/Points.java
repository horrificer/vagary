package com.horrific.common.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Points<T> extends ChartDTO implements Serializable {
    private List<T> value;

    public Points(String name, List<T> value){
        super(name);
        this.value = value;
    }
}
