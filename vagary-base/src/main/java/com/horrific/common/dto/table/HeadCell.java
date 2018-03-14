package com.horrific.common.dto.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeadCell implements Serializable{


    /**
     * 隱藏
     */
    private String hidden;

    /**
     * 中文名
     */
    private String title;

    /**
     * 英文名
     */
    private String key;

    public HeadCell(String title, String key) {
        this.title = title;
        this.key = key;
    }

}
