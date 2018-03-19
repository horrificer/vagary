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

    abstract void validate(String sql);
}
