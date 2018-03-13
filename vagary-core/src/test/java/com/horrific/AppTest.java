package com.horrific;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    @Test
    public void testSql() {

        String json = "{\n" +
                "\t\"columns\":\"sum(a), b, c\",\t\n" +
                "\t\"whereOn\":\"b=1,c=2\",\n" +
                "\t\"whereOr\":\"b=2,c=3\",\n" +
                "\t\"groupBy\":\"a\",\n" +
                //"\t\"orderBy\":\"b asc,c desc\",\n" +
                //"\t\"having\":\"a>100\",\n" +
                //"\t\"size\":1,\n" +
                //"\t\"offset\":2,\n" +
                "\t\"chartType\":1\n" +
                "}";
        Data data = JSONObject.parseObject(json, Data.class);
        System.out.println(new MysqlBuilder().buildSql(data, "test_table", null));
    }
}
