package com.horrific.chartconvert;


import com.google.common.collect.Lists;
import com.horrific.Param;
import com.horrific.common.dto.ChartInfo;
import com.horrific.common.dto.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author wanghong12
 * @since 2018-3-13
 */

public abstract class AbstractChartConvert<T> implements ChartConvert<T> {

    public static final String DEFAULT_DIMENSION = "bs";
    public static final String DEFAULT_MEASURE = "gmv";
    public static final String DEFAULT_DIVIDE_VALUE = "#ALL";

    /**
     * @param data   query information
     * @param params query information
     * @return
     */
    @Override
    public T handle(List<Map<String, Object>> data, Param params) {
        ChartInfo chartInfo = argumentParse(data, params);
        return convert(chartInfo);
    }

    /**
     * 根据chartInfo转换成可视化组件所需格式
     *
     * @param chartInfo
     * @return
     */
    public abstract T convert(ChartInfo chartInfo);

    /**
     * @param chartType chart type
     * @return
     */
    @Override
    public abstract boolean beanIndicate(Integer chartType);

    /**
     * @param data   query information
     * @param params queryParams
     * @return
     */
    public ChartInfo argumentParse(List<Map<String, Object>> data, Param params) {
        //获取图表类型
        Integer chartType = params.getChartType();

        String divide = getDivideValue(params);

        //获取维度
        List<Field> dims = toField(params.getGroupBy());
        //获取度量
        List<Field> measures = toField(params.getColumns());
        //获取第一个维度caption
        String dim = dims.size() == 0 ? DEFAULT_DIMENSION : dims.get(0).getCaption();
        //获取第一个度量caption
        String measure = measures.size() == 0 ? DEFAULT_MEASURE : measures.get(0).getCaption();
        return new ChartInfo(chartType, data, dims, measures, dim, measure, params.getTotal(), divide);
    }

    /**
     * 将参数转化为维度和指标
     *
     * @param str
     * @return
     */
    private static List<Field> toField(String str) {
        List<Field> fields = Lists.newArrayList();
        if (StringUtils.isEmpty(str)) {
            return fields;
        }
        String[] split = str.split(",");
        String[][] array = new String[split.length][];
        for (int i = 0; i < split.length; i++) {
            array[i] = split[i].trim().split("\\s+");
            if (array[i].length == 1) {
                fields.add(new Field(array[i][0], array[i][0]));
            } else {
                fields.add(new Field(array[i][0], array[i][1]));
            }

        }

        return fields;
    }

    public static String getDivideValue(Param params) {
        String divide = params.getDivide() == null ? DEFAULT_DIMENSION : params.getDivide();
        if (StringUtils.isEmpty(params.getWhereOn())) {
            return DEFAULT_DIVIDE_VALUE;
        }
        String[] split = params.getWhereOn().split(",");
        for(String e : split) {
            if (e.contains(divide.trim())) {
                return e.substring(e.indexOf("\'") + 1, e.length() - 1);
            }
        }
        return DEFAULT_DIVIDE_VALUE;
    }

}
