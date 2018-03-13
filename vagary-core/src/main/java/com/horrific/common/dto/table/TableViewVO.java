package com.horrific.common.dto.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableViewVO implements Serializable {

    private Integer chartType;

    private List<HeadCell> titleName;

    private List<Map<String, Object>> titleList;

    private Integer total ;

    public TableViewVO(Integer chartType, List<HeadCell> titleName, List<Map<String, Object>> titleList) {
        this.chartType = chartType;
        this.titleName = titleName;
        this.titleList = titleList;
    }

}
