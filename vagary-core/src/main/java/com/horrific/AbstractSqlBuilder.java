package com.horrific;

import com.horrific.sqlbuilder.builder.QueryBuilder;
import com.horrific.sqlbuilder.select.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author wanghong12
 */
public class AbstractSqlBuilder {

    public static final String COMMA = ",";

    public Select select(Data data, String table) {
        QueryBuilder builder = new QueryBuilder();
        Select select = builder.select();

        if (StringUtils.isEmpty(data.getColumns())) {
            select.all();
        } else {
            String[] clumns = data.getColumns().split(COMMA);
            Arrays.stream(clumns).forEach(s -> select.column(s));
        }
        return select;
    }

    public Where where(Data data, From from, Join join) {
        final Where[] where = new Where[1];
        if (join != null) {
            where[0] = join.where();
        } else {
            where[0] = from.where();
        }

        if (StringUtils.isNotEmpty(data.getWhereOn())) {
            String[] wheres = data.getWhereOn().split(COMMA);
            Arrays.stream(wheres).forEach(w -> where[0].and(w));
        }
        if (StringUtils.isNotEmpty(data.getWhereOr())) {
            String[] wheres = data.getWhereOr().split(COMMA);
            Arrays.stream(wheres).forEach(w -> where[0].or(w));
        }
        return where[0];
    }

    public GroupBy groupBy(Data data, GroupBy groupBy) {
        if (StringUtils.isNotEmpty(data.getGroupBy())) {
            String[] groupBys = data.getGroupBy().split(COMMA);
            Arrays.stream(groupBys).forEach(g -> groupBy.column(g));
        }
        return groupBy;
    }

    public Having having(Data data, GroupBy groupBy) {
        if (StringUtils.isNotEmpty(data.getHaving())) {
            Having having = groupBy.having();
            String[] hs = data.getHaving().split(COMMA);
            Arrays.stream(hs).forEach(h -> having.condition(h));
            return having;
        }
        return null;
    }

}
