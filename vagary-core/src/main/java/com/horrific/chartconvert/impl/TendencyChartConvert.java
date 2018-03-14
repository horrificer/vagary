package com.horrific.chartconvert.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.horrific.chartconvert.AbstractChartConvert;
import com.horrific.common.dto.ChartInfo;
import com.horrific.common.dto.Field;
import com.horrific.common.dto.chart.DefaultChartVO;
import com.horrific.common.dto.chart.ListChartDTO;
import com.horrific.common.dto.chart.Point;
import com.horrific.common.enums.CharTypeEnum;
import com.horrific.common.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 可视化组件：线图、柱图、柱线图、条状图
 * @author wanghong12
 * @version 创建时间：2017/10/15
 */
@Component
public class TendencyChartConvert extends AbstractChartConvert<DefaultChartVO<String,ListChartDTO<Point>>> {

    @Override
    public DefaultChartVO<String, ListChartDTO<Point>> convert(ChartInfo chartInfo) {
        List<String> dimension = Lists.newArrayList();
        Map<String, List<Point>> pointMap = Maps.newHashMap();
        List<ListChartDTO<Point>> measure = Lists.newArrayList();
        if(chartInfo.getDataList() == null) {
            return new DefaultChartVO(chartInfo.getChartType(), dimension, measure);
        }

        chartInfo.getDataList().forEach(map -> {
            String dimensionValue = map.get(chartInfo.getDim()) == null ? null : DateUtil.toValue(map.get(chartInfo.getDim())).toString();
            dimension.add(dimensionValue);
            map.forEach((String key, Object value) -> {
                if (isBelong(chartInfo.getMeasures(), key)) {
                    List<Point> points = pointMap.get(key);
                    if (points != null) {
                        points.add(new Point(dimensionValue, value));
                    } else {
                        pointMap.put(key, Lists.newArrayList(new Point(dimensionValue, value)));
                    }
                }
            });
        });

        pointMap.forEach((key, value) -> measure.add(new ListChartDTO(key, value)));
        return new DefaultChartVO(chartInfo.getChartType(), dimension, measure);
    }

    @Override
    public boolean beanIndicate(Integer chartType) {
        return chartType == CharTypeEnum.LINE.code || chartType == CharTypeEnum.BAR.code || chartType == CharTypeEnum.STRIP.code;
    }

    private boolean isBelong(final List<Field> measures, final String measure) {
        if (measures == null) {
            return false;
        }
        for (Field field : measures) {
            if (field.getCaption().equals(measure)) {
                return true;
            }
        }
        return false;
    }
}
