package com.horrific.chartconvert;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    public static final String DEFAULT_DIMENSION = "维度";
    public static final String DEFAULT_MEASURE = "度量";
    public static final String FIRST_DIM_ORIGIN_VALUE = "firstDimOriginValue";

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

        //获取维度
        List<Field> dims = toField(params.getColumns());
        //获取度量
        List<Field> measures = toField(params.getGroupBy());
        //获取第一个维度caption
        String dim = dims.size() == 0 ? DEFAULT_DIMENSION : dims.get(0).getCaption();
        //获取第一个度量caption
        String measure = measures.size() == 0 ? DEFAULT_MEASURE : measures.get(0).getCaption();
        return new ChartInfo(chartType, data, dims, measures, dim, measure);
    }

    /**
     * 将参数转化为维度和指标
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
            array[i] = split[i].split("\\s+");
            if (array[i].length == 1) {
                fields.add(new Field(array[i][0], array[i][0]));
            } else {
                fields.add(new Field(array[i][0], array[i][1]));
            }

        }

        return fields;
    }

    /**
     * 构建联动映射：key是翻译后的name，value是翻译前的id（即联动其他图表的字段值）
     * @param chartInfo
     * @return
     */
    protected Map buildLinkageMap(ChartInfo chartInfo){
        Map linkage = Maps.newLinkedHashMap();
        if(DEFAULT_DIMENSION.equals(chartInfo.getDim())) {
            return linkage;
        }

        List<Map<String,Object>> data = chartInfo.getDataList();
        if(data == null){
            return linkage;
        }

        data.forEach(map -> {
            //为空时的默认值需要统一，TODO
            Object value = map.get(chartInfo.getDim()) == null? "": map.get(chartInfo.getDim());
            Object key = map.get(FIRST_DIM_ORIGIN_VALUE) == null ? value : map.get(FIRST_DIM_ORIGIN_VALUE);
            linkage.put(value,key);
        });
        return linkage;
    }
}
