package com.horrific.common.utils;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * POI组件生成工具类
 *
 * @author: huyong
 * @since: 2017/8/16 12:30
 */
public class POIComponentUtil {

    /**
     * 生成Excel对象
     *
     * @param lineNum 生成Excel时，超过该条数，把内存数据写入到磁盘
     * @return
     */
    public static SXSSFWorkbook makeSXSSFWorkbook(int lineNum) {
        return new SXSSFWorkbook(lineNum);
    }

    /**
     * 生成sheet
     *
     * @param book      excel对象
     * @param sheetName sheet名字
     * @return
     */
    public static SXSSFSheet makeSXSSFSheet(SXSSFWorkbook book, String sheetName) {
        return (SXSSFSheet) book.createSheet(sheetName);
    }

    /**
     * 生成row
     *
     * @param sheet
     * @param rowNum 行的索引(即：第几行)
     * @return
     */
    public static SXSSFRow makeSXSSFRow(SXSSFSheet sheet, int rowNum) {
        return (SXSSFRow) sheet.createRow(rowNum);
    }

    /**
     * 生成一行的一个单元格
     *
     * @param row
     * @param columnNum 列索引
     * @return
     */
    public static SXSSFCell makeSXSSFCell(SXSSFRow row, int columnNum) {
        return (SXSSFCell) row.createCell(columnNum);
    }

}
