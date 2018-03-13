package com.horrific.chartconvert.impl;


import com.google.common.collect.Lists;
import com.horrific.chartconvert.AbstractChartConvert;
import com.horrific.common.dto.ChartInfo;
import com.horrific.common.dto.Field;
import com.horrific.common.dto.table.HeadCell;
import com.horrific.common.dto.table.TableViewVO;
import com.horrific.common.enums.CharTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ChartConvert Object to TableViewVO
 *
 * @author wanghong12 2017-09-29
 */
@Component
public class TableChartConvert extends AbstractChartConvert<TableViewVO> {

    @Override
    public TableViewVO convert(ChartInfo chartInfo) {
        List<Field> dimAndMeas = Lists.newArrayList();

        dimAndMeas.addAll(chartInfo.getDims());dimAndMeas.addAll(chartInfo.getMeasures());

        List<Map<String, Object>> list = chartInfo.getDataList();

        List<HeadCell> headCellList = Lists.newArrayList();

        dimAndMeas.forEach(each -> headCellList.add(new HeadCell(each.getCaption(), each.getCaption())));

        TableViewVO tableViewVO = new TableViewVO(chartInfo.getChartType(), headCellList, list);

        //tableViewVO.setTotal(chartInfo.getDataList().getTotal());

        return tableViewVO;
    }

    @Override
    public boolean beanIndicate(final Integer chartType) {
        if (chartType == CharTypeEnum.TABLE.code || chartType == CharTypeEnum.LIST.code) {
            return true;
        }
        return false;
    }
}
