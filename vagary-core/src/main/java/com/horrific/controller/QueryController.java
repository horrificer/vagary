package com.horrific.controller;

import com.alibaba.fastjson.JSONObject;
import com.horrific.Param;
import com.horrific.common.dto.table.TableViewVO;
import com.horrific.common.response.ServiceResponse;
import com.horrific.common.tools.downloadexcel.local.DownloadTable;
import com.horrific.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 查询
     * @param param
     * @param tableName
     * @return
     */
    @RequestMapping(value = "/_search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ServiceResponse query(@RequestBody Param param, @PathVariable("table_name") String tableName) {

        param.setTable(tableName);

        try {
            return queryService.query(param);
        } catch (Exception e) {
            return ServiceResponse.failure("查询失败");
        }

    }

    /**
     * 下载
     * @param param
     * @param tableName
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/_download", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=UTF-8")
    public void download(@RequestParam("param") String param, @PathVariable("table_name") String tableName, HttpServletResponse response) throws Exception {
        Param param1 = JSONObject.parseObject(param, Param.class);
        param1.setTable(tableName);
        param1.setChartType(1);
        ServiceResponse<TableViewVO> viewVO = queryService.query(param1);
        DownloadTable.writeContentToPage(viewVO.getData(), param1.getTitle(), response);
    }

    /**
     * 更新
     * @param sql
     * @return
     */
    @RequestMapping(value = "/_update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ServiceResponse update(String sql) {
        try {
            queryService.update(sql);
        } catch (Exception e) {
            return ServiceResponse.failure("查询失败");
        }
        return ServiceResponse.ok("更新成功！");
    }

}
