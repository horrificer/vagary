package com.horrific.buildsql;

import com.horrific.Param;
import com.horrific.sqlbuilder.builder.QueryBuilder;
import com.horrific.sqlbuilder.select.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author wanghong12
 */
public abstract class AbstractSqlBuilder {

    public static final String COMMA = ",";

    public Select select(Param param) {
        QueryBuilder builder = new QueryBuilder();
        Select select = builder.select();

        if (StringUtils.isEmpty(param.getColumns())) {
            select.all();
        } else {
            String[] clumns = param.getColumns().split(COMMA);
            Arrays.stream(clumns).forEach(s -> select.column(s));
        }
        return select;
    }

    public Where where(Param param, From from, Join join) {
        final Where[] where = new Where[1];
        if (join != null) {
            where[0] = join.where();
        } else {
            where[0] = from.where();
        }

        if (StringUtils.isNotEmpty(param.getWhereOn())) {
            String[] wheres = param.getWhereOn().split(COMMA);
            Arrays.stream(wheres).forEach(w -> where[0].and(w));
        }
        if (StringUtils.isNotEmpty(param.getWhereOr())) {
            String[] wheres = param.getWhereOr().split(COMMA);
            Arrays.stream(wheres).forEach(w -> where[0].or(w));
        }
        return where[0];
    }

    public GroupBy groupBy(Param param, GroupBy groupBy) {
        if (StringUtils.isNotEmpty(param.getGroupBy())) {
            String[] groupBys = param.getGroupBy().split(COMMA);
            Arrays.stream(groupBys).forEach(g -> groupBy.column(g));
        }
        return groupBy;
    }

    public Having having(Param param, GroupBy groupBy) {
        if (StringUtils.isNotEmpty(param.getHaving())) {
            Having having = groupBy.having();
            String[] hs = param.getHaving().split(COMMA);
            Arrays.stream(hs).forEach(h -> having.condition(h));
            return having;
        }
        return null;
    }

}
