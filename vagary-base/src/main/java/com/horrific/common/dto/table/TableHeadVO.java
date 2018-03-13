package com.horrific.common.dto.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanghong12
 * @since 2018-3-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableHeadVO {

    /**
     * 表头名称
     */
    private String title;

    /**
     * 表头key
     */
    private String data;

    /**
     * 标识
     */
    private Integer id;

}
