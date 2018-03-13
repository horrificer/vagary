package com.horrific.buildsql;

import com.horrific.Param;
import com.horrific.sqlbuilder.select.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wanghong12
 * @version 2018-3-12
 */
public class MysqlBuilder extends AbstractSqlBuilder {

    public String buildSql(Param param) {

        From from = select(param).from().table(param.getTable());

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
