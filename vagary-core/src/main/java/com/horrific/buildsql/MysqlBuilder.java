package com.horrific.buildsql;

import com.horrific.Param;
import com.horrific.sqlbuilder.select.From;
import com.horrific.sqlbuilder.select.GroupBy;
import com.horrific.sqlbuilder.select.Having;
import com.horrific.sqlbuilder.select.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author wanghong12
 * @version 2018-3-12
 */
@Component
public class MysqlBuilder extends AbstractSqlBuilder {

    public String buildSql(Param param) {

        String table = param.getTable();
        if (param.getPage()) {
            table = param.getTable() + COMMA + String.format(WHERE_NUM, param.getSize() * param.getOffset());
        }

        From from = select(param).from().table(table);

        Join join = null;
        if (StringUtils.isNotEmpty(param.getJoinTable())) {
            join = from.innerJoin(param.getJoinTable());
        }

        if (StringUtils.isEmpty(param.getGroupBy()) && StringUtils.isEmpty(param.getOrderBy())) {
            return where(param, from, join).limit(param.getOffset(), param.getSize()).toString();
        }

        if (StringUtils.isEmpty(param.getGroupBy()) && StringUtils.isNotEmpty(param.getOrderBy())) {
            return where(param, from, join).orderBy(param.getOrderBy().split(COMMA)).limit(param.getOffset(), param.getSize()).toString();
        }

        GroupBy groupBy = groupBy(param, where(param, from, join).groupBy());

        if (StringUtils.isEmpty(param.getHaving()) && StringUtils.isEmpty(param.getOrderBy())) {
            return groupBy.limit(param.getOffset(), param.getSize()).toString();
        }

        if (StringUtils.isEmpty(param.getHaving()) && StringUtils.isNotEmpty(param.getOrderBy())) {
            return groupBy.orderBy(param.getOrderBy().split(COMMA)).limit(param.getOffset(), param.getSize()).toString();
        }

        Having having = having(param, groupBy);

        if (StringUtils.isEmpty(param.getOrderBy())) {
            return having.limit(param.getOffset(), param.getSize()).toString();
        }

        if (StringUtils.isNotEmpty(param.getOrderBy())) {
            return having.orderBy(param.getOrderBy().split(COMMA)).limit(param.getOffset(), param.getSize()).toString();
        }

        return null;
    }

}
