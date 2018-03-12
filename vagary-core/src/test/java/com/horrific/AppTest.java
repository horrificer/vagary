package com.horrific;

import com.horrific.sqlbuilder.builder.QueryBuilder;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    @Test
    public void testSql() {

        String[] a = {"a asc", "b desc"};
        String sql = new QueryBuilder()
                .select()
                .all()
                .from()
                .table("test_table")
                .where()
                .and("a=1")
                .limit(1,4)
                .toString();
        System.out.println(sql);
    }
}
