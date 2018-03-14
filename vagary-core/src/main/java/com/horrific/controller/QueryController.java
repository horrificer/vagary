package com.horrific.controller;

import com.horrific.Param;
import com.horrific.common.response.ServiceResponse;
import com.horrific.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 所有查询请求统一入口
 *
 * @author wanghong12
 * @since 2018-3-13
 */
@Slf4j
@Controller
@RequestMapping("/toplife/{table_name}")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/_search", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServiceResponse query(@RequestBody Param param, @PathVariable("table_name") String tableName) {

        param.setTable(tableName);

        try {
            return queryService.query(param);
        } catch (Exception e) {
            log.error("query error queryParams:{}", param.toString());
            log.error("query error:", e);
            return ServiceResponse.failure("查询失败");
        }

    }

}
