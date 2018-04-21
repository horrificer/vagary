package com.horrific.query;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.google.common.collect.Maps;
import com.horrific.Param;
import com.horrific.buildsql.MysqlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wanghong12
 * @since 2018-3-13
 */
@Slf4j
public abstract class AbstractQuery {

    public static final String COUNT_SQL = "SELECT count(*) num FROM (%s) t";

    @Autowired
    private MysqlBuilder builder;

    @Autowired
    private DataSource dataSource;

    abstract <T> T query(Param param);

    /*static {
        Properties properties = new Properties();
        properties.put("url", "jdbc:elasticsearch://192.168.200.122:20300");
        try {
            dataSource = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 查询操作
     *
     * @param param
     * @return
     */
    public List<Map<String, Object>> doQuery(Param param) {

        String sql = builder.buildSql(param);

        log.info("sql:{}", sql);

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

        //todo validate sql

        return template.queryForList(sql, Maps.newHashMap());
    }

    /**
     * 分页时候做求和运算
     *
     * @param param
     */
    public void countQuery(Param param) {
        param.setPage(false);
        param.setOrderBy(null);
        param.setSize(null);
        param.setOffset(null);
        String sql = builder.buildSql(param);
        sql = String.format(COUNT_SQL, sql);
        log.info("sql:{}", sql);
        JdbcTemplate template = new JdbcTemplate(dataSource);
        Map<String, Object> map = template.queryForMap(sql);
        param.setTotal(String.valueOf(map.get("num")));
    }

    /**
     * 对sql进行验证
     *
     * @param sql
     */
    abstract void validate(String sql);

    /**
     * 更新操作
     *
     * @param sql
     */
    public void doUpdate(String sql) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        template.update(sql, new HashMap<>());
    }
}
