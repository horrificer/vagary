package com.horrific.chartconvert.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.horrific.chartconvert.AbstractChartConvert;
import com.horrific.common.dto.ChartInfo;
import com.horrific.common.enums.CharTypeEnum;
import com.horrific.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 对查询结果不做任何处理
 *
 * @author wanghong12
 * @since 2018-3-16
 */
@Component
public class Top2MapConvert extends AbstractChartConvert<Map<String, List>> {
    @Override
    public Map<String, List> convert(ChartInfo chartInfo) {

        Map<String, List> listMap = Maps.newHashMap();

        if (StringUtils.isNotEmpty(chartInfo.getTotal())) {
            listMap.put("total", Lists.newArrayList(chartInfo.getTotal()));
        }

        chartInfo.getDataList().forEach(map -> {
            String dimensionValue = map.get(chartInfo.getDim()) == null ? null : DateUtil.toValue(map.get(chartInfo.getDim())).toString();
            if (listMap.get(dimensionValue) != null) {
                listMap.get(dimensionValue).add(map);
            } else {
                listMap.put(dimensionValue, Lists.newArrayList(map));
            }
        });
        return listMap;
    }

    @Override
    public boolean beanIndicate(Integer chartType) {
        if (chartType == CharTypeEnum.DEFAULT.code) {
            return true;
        }
        return false;
    }
}
