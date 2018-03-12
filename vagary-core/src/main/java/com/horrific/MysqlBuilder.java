package com.horrific;

import com.horrific.sqlbuilder.select.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wanghong12
 * @version 2018-3-12
 */
public class MysqlBuilder extends AbstractSqlBuilder {

    public String buildSql(Data data, String table, String joinTable) {

        From from = select(data, table).from().table(table);

        Join join = null;
        if (StringUtils.isNotEmpty(joinTable)) {
            join = from.innerJoin(joinTable);
        }

        if (StringUtils.isEmpty(data.getGroupBy()) && StringUtils.isEmpty(data.getOrderBy())) {
            return where(data, from, join).limit(data.getOffset(), data.getSize()).toString();
        }

        if (StringUtils.isEmpty(data.getGroupBy()) && StringUtils.isNotEmpty(data.getOrderBy())) {
            return where(data, from, join).orderBy(data.getOrderBy().split(COMMA)).limit(data.getOffset(), data.getSize()).toString();
        }

        GroupBy groupBy = groupBy(data, where(data, from, join).groupBy());

        if (StringUtils.isEmpty(data.getHaving()) && StringUtils.isEmpty(data.getOrderBy())) {
            return groupBy.limit(data.getOffset(), data.getSize()).toString();
        }

        if (StringUtils.isEmpty(data.getHaving()) && StringUtils.isNotEmpty(data.getOrderBy())) {
            return groupBy.orderBy(data.getOrderBy().split(COMMA)).limit(data.getOffset(), data.getSize()).toString();
        }

        Having having = having(data, groupBy);

        if (StringUtils.isEmpty(data.getOrderBy())) {
            return having.limit(data.getOffset(), data.getSize()).toString();
        }

        if (StringUtils.isNotEmpty(data.getOrderBy())) {
            return having.orderBy(data.getOrderBy().split(COMMA)).limit(data.getOffset(), data.getSize()).toString();
        }

        return null;
    }

}
