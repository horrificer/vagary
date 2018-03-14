package com.horrific.service;

import com.horrific.Param;
import com.horrific.common.response.ServiceResponse;
import com.horrific.query.MySqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wanghong12
 * @since 2018-3-13
 */

@Component
public class QueryService {

    @Autowired
    private MySqlQuery query;

    public <T> ServiceResponse<T> query(Param params) {
        T data = query.query(params);
        if (data == null) {
            return ServiceResponse.failure("暂无数据");
        }
        return ServiceResponse.ok(data);
    }
}
