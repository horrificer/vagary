package com.horrific.query;

import com.horrific.Param;
import com.horrific.buildsql.MysqlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghong12
 * @since 2018-3-13
 */
@Slf4j
public abstract class AbstractQuery {

    public static final String COUNT = "count(*) num";

    @Autowired
    private MysqlBuilder builder;

    @Autowired
    private DataSource dataSource;

    abstract <T> T query(Param param);

    public List<Map<String, Object>> doQuery(Param param) {

        String sql = builder.buildSql(param);

        log.info("sql:{}", sql);

        //todo validate sql

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

        return template.queryForList(sql, new HashMap<>());
    }

    public void countQuery(Param param) {
        param.setPage(false);
        param.setColumns(COUNT);
        param.setOrderBy(null);
        param.setSize(null);
        param.setOffset(null);
        param.setTotal(String.valueOf(doQuery(param).get(0).get("num")));
    }

    abstract void validate(String sql);
}
