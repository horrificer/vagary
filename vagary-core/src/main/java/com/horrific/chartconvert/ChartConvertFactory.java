package com.horrific.chartconvert;

import com.horrific.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A factory, provide a convert bean by chartType for each search.
 *
 * @author wanghong12 2017-09-29
 */
@Component
public class ChartConvertFactory {

    private List<ChartConvert> convertList;

    @Autowired
    public void setConvertList(List<ChartConvert> convertList) {
        this.convertList = convertList;
    }

    /**
     * get convert instance
     *
     * @param params
     * @return
     */
    public ChartConvert getInstance(final Param params) {

        int chartType = params.getChartType();

        for (ChartConvert convert : convertList) {
            if (convert.beanIndicate(chartType)) {
                return convert;
            }
        }

        return null;

    }
}
