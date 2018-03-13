package com.horrific.chartconvert;

import com.horrific.Param;

import java.util.List;
import java.util.Map;

/**
 * ChartConvert List<Map<String, Object>> to VO
 *
 * @author wanghong12 2017-09-29
 * @param <T>
 */
public interface ChartConvert<T> {

    /**
     * According to the queryParams chart types, change the information
     * through the handle method into the front data format
     *
     * @param data query information
     * @param params query information
     * @return VO needed by web application
     */
    T handle(List<Map<String, Object>> data, Param params);


    /**
     * Identification of the current bean via chartType
     *
     * @param chartType chart type
     * @return true or false
     */
    boolean beanIndicate(Integer chartType);
}
