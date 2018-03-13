package com.horrific.common.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @author duanmenghua
 * @date 2017年8月7日 下午5:39:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Field implements Serializable{
    /**
     * 字段名称
     */
    private String field;

    /**
     * 字段显示名称/别名
     */
    private String caption;




}
