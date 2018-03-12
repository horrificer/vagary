package com.horrific;

import com.horrific.sqlbuilder.builder.QueryBuilder;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    @Test
    public void testSql() {
        String sql = new QueryBuilder()
                .select()
                .all()
                .from()
                .table("test_table")
                .where()
                .and("a=1")
                .groupBy()
                .column("a")
                .having()
                .condition("sum(a)>0")
                .toString();
        System.out.println(sql);
    }
}
