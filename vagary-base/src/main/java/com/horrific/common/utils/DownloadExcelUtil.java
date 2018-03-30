package com.horrific.common.utils;

import com.horrific.common.dto.table.HeadCell;
import com.horrific.common.dto.table.TableViewVO;
import com.horrific.common.enums.DatePatternEnum;
import com.horrific.common.tools.downloadexcel.local.ColumnInfo;
import com.horrific.common.tools.downloadexcel.local.ExcelTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;


/**
 * @author wanghong12
 * @date 2018-3-28
 */

@Slf4j
public class DownloadExcelUtil {

    public static final String EXCELSUFFIX_XLSX = ".xlsx";

    /**
     * excel临时文件路径
     */
    private static final String EXCELTMPPATH = "/tmp/";

    /**
     * 生成Excel时，超过限制条数后，把内存数据写入到磁盘
     */
    private static final int excelSize = 10000;

    /**
     * 下载
     *
     * @param data     待拼装的数据
     * @param template Excel模板
     * @return 临时存储的文件名
     */
    public static <T> String download(List<T> data, ExcelTemplate template) throws Exception {

        if (data == null) {
            return null;
        }

        return downloadAccordingToTemplate("sheet", data, template);

    }

    /**
     * 根据表格VO进行Excel下载
     *
     * @param title   标题名
     * @param tableVo 表格VO
     * @return Excel的名字
     */
    public static SXSSFWorkbook download(String title, TableViewVO tableVo) throws Exception {

        SXSSFWorkbook workbook = POIComponentUtil.makeSXSSFWorkbook(excelSize);

        if (tableVo == null) {
            return workbook;
        }

        SXSSFSheet sheet = POIComponentUtil.makeSXSSFSheet(workbook, "sheet1");

        //当前行数
        Integer currRow = 0;

        //根据表头的标记(是否下载[download])，来过滤出需要下载的字段
        List<String> downloadFieldList = getExcludeIndex(tableVo.getTitleName());

        //构建Excel的标题
        currRow = doBuildExcelTitle(sheet, currRow, title, tableVo.getTitleName(), downloadFieldList, getCellStyleTitle(workbook));

        //构建Excel的表头
        currRow = doBuildExcelHead(sheet, currRow, tableVo.getTitleName(), downloadFieldList, getCellStyleHead(workbook));

        //构建Excel的表体
        doBuildExcelBody(sheet, currRow, tableVo.getTitleList(), downloadFieldList, getCellStyleBody(workbook));

        return workbook;

    }

    /**
     * 获取需要下载的字段的名字
     *
     * @param head 表头
     * @return
     */
    private static List<String> getExcludeIndex(List<HeadCell> head) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < head.size(); i++) {

            HeadCell headCell = head.get(i);
            //if (Boolean.valueOf(headCell.getDownload())) {
            list.add(headCell.getKey());
            //}
        }

        return list;

    }

    /**
     * 构建Excel的标题
     *
     * @param sheet             excel某个sheet
     * @param currRow           当前行数
     * @param title             标题
     * @param head              表头
     * @param downloadFieldList 需要下载字段名字集合
     * @param titleCellStyle    标题样式
     */
    private static int doBuildExcelTitle(SXSSFSheet sheet, int currRow, String title, List<HeadCell> head, List<String> downloadFieldList, CellStyle titleCellStyle) {

        log.info("生成标题-{}，标题开始行数：{}，列数：{}", title, currRow, downloadFieldList.size());

        SXSSFRow row = POIComponentUtil.makeSXSSFRow(sheet, currRow++);
        row.setHeightInPoints(30);

        SXSSFCell cell = POIComponentUtil.makeSXSSFCell(row, 0);
        cell.setCellValue(title.replace(".xlsx", ""));
        cell.setCellStyle(titleCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), row.getRowNum(), downloadFieldList.size() - 1));

        return currRow;

    }

    /**
     * 构建Excel的表头
     *
     * @param sheet             excel某个sheet
     * @param currRow           当前行数
     * @param head              表头
     * @param downloadFieldList 需要下载字段名字集合
     * @param headCellStyle     表头样式
     */
    private static int doBuildExcelHead(SXSSFSheet sheet, int currRow, List<HeadCell> head, List<String> downloadFieldList, CellStyle headCellStyle) {

        log.info("生成表头，表头列数：{}，表头开始行数：{}", downloadFieldList.size(), currRow);

        SXSSFRow row = POIComponentUtil.makeSXSSFRow(sheet, currRow++);

        Map<String, HeadCell> map = CollectionUtils.listToMap(head, "", "key");

        //写入数据的单元格的位置
        int cellIndex = 0;
        for (int i = 0; i < downloadFieldList.size(); i++) {
            HeadCell headCell = map.get(downloadFieldList.get(i));
            SXSSFCell cell = POIComponentUtil.makeSXSSFCell(row, cellIndex++);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(headCell.getTitle());
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }

        return currRow;

    }

    /**
     * 构建Excel的表体
     *
     * @param sheet             excel某个sheet
     * @param currRow           当前行数
     * @param datas             数据集合，数据为自定义类型
     * @param downloadFieldList 需要下载字段名字集合
     * @param bodyCellStyle     数据单元格样式
     */
    private static int doBuildExcelBody(SXSSFSheet sheet, int currRow, List datas, List<String> downloadFieldList, CellStyle bodyCellStyle) {

        log.info("生成表体，表体数量-{}，表体开始行数：{}，列数：{}", datas.size(), currRow, downloadFieldList.size());

        long start = System.currentTimeMillis();
        for (int i = 0; i < datas.size(); i++) {
            SXSSFRow row = POIComponentUtil.makeSXSSFRow(sheet, currRow++);
            makeRow(row, datas.get(i), downloadFieldList, bodyCellStyle);
        }

        log.info("生成表体，耗时：{}", (System.currentTimeMillis() - start));

        return currRow;

    }

    /**
     * 创建一行的数据
     *
     * @param row               Excel的行对象
     * @param data              一条数据
     * @param downloadFieldList 需要下载字段名字集合
     */
    private static void makeRow(SXSSFRow row, Object data, List<String> downloadFieldList, CellStyle cellStyle) {

        Map<String, Object> map = (Map<String, Object>) data;

        //写入数据的单元格的位置
        int cellIndex = 0;
        for (int i = 0; i < downloadFieldList.size(); i++) {
            Object cellValue = map.get(downloadFieldList.get(i));
            SXSSFCell cell = POIComponentUtil.makeSXSSFCell(row, cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(convertAttrMember(cellValue));
        }
    }


    /**
     * 把值转换为字符串格式
     *
     * @param data 待转换的数据
     * @return
     */
    private static String convertValue(Object data) {

        if (data == null) {
            return "";
        }

        if (data instanceof Date) {
            return DateFormatUtil.format((Date) data, DatePatternEnum.DATE_FORMAT);
        }

        return data.toString();

    }

    /**
     * 根据Excel模板进行下载(适合下载数据不大的情况，因为生成的Excel，是存放在内存中的)
     *
     * @param sheetName excel sheet的名字
     * @param data      待拼装的数据
     * @param template  Excel模板
     * @return 临时存储的文件名
     */
    private static <T> String downloadAccordingToTemplate(String sheetName, List<T> data, ExcelTemplate template) throws Exception {

        SXSSFWorkbook book = (SXSSFWorkbook) WorkbookFactory.create(new File(template.getTemplateName()));

        Sheet sheet = getSheet(book, sheetName);

        for (int i = 0; i < data.size(); i++) {
            makeRow(sheet, data.get(i), template, i);
        }

        //把Excel写入临时文件中
        String tmpPath = new StringBuilder(EXCELTMPPATH).append(UUID.randomUUID()).append(EXCELSUFFIX_XLSX).toString();
        log.debug("生成的临时文件为：{}", tmpPath);
        try (FileOutputStream fos = new FileOutputStream(new File(tmpPath))) {
            book.write(fos);
            return tmpPath;
        }

    }

    /**
     * 获取Excel的sheet
     *
     * @param book      Excel对象
     * @param sheetName sheet名字
     * @return
     */
    private static Sheet getSheet(Workbook book, String sheetName) {

        Sheet sheet = book.getSheet(sheetName);

        if (sheet == null) {
            sheet = book.getSheetAt(0);
            book.setSheetName(0, sheetName);
        }

        return sheet;

    }

    /**
     * 创建一行
     *
     * @param sheet           Excel的sheet
     * @param data            待写入Excel一行的数据
     * @param template        数据行第一行每个单元格的坐标信息
     * @param currentRowIndex 处理到第几行的索引
     * @param <T>
     */
    private static <T> void makeRow(Sheet sheet, T data, ExcelTemplate template, int currentRowIndex) {

        Map<String, Object> dataMap;

        if (data instanceof Map) {
            dataMap = (Map<String, Object>) data;
        } else {
            dataMap = BeanUtil.convertObjectToMap(data);

        }

        Map<String, ColumnInfo> columnInfoMap = template.getBodyInfo();

        Set<Map.Entry<String, ColumnInfo>> entries = columnInfoMap.entrySet();

        Row row = getRow(sheet, template, currentRowIndex);

        for (Map.Entry<String, ColumnInfo> entry : entries) {
            makeCell(row, dataMap, entry.getValue(), sheet, template);
        }

    }


    /**
     * 获取sheet中的一行，如果模板Excel文件有那一行，直接返回该行，否者创建新行
     *
     * @param sheet           Excel中的一个sheet
     * @param template        Excel模板
     * @param currentRowIndex 当前处理的第几条数据
     * @return
     */
    private static Row getRow(Sheet sheet, ExcelTemplate template, int currentRowIndex) {

        //当前待写数据行的索引
        int rowIndex = template.getBodyStartIndex() + currentRowIndex;

        Row row = sheet.getRow(rowIndex);

        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        return row;

    }

    /**
     * 创建一个单元格
     *
     * @param row        一行
     * @param dataMap    待填充一行的数据
     * @param columnInfo 填充一行
     * @param sheet      excel的sheet
     * @param template   excel模板
     * @param sheet
     */
    private static void makeCell(Row row, Map<String, Object> dataMap, ColumnInfo columnInfo, Sheet sheet, ExcelTemplate template) {

        Cell cell = getCell(row, columnInfo);
        cell.setCellValue(fetchValue(dataMap, columnInfo));
        cell.setCellStyle(getCellStypeFirstDataRowReferColumn(sheet, template, columnInfo));
        sheet.autoSizeColumn(columnInfo.getColumnIndex());

    }

    /**
     * 获取一个单元格,如果模板Excel文件指定行单元格存在，返回该单元格，否者新创建一个单元格
     *
     * @param row        Excel中第一行
     * @param columnInfo 单元格信息
     * @return
     */
    private static Cell getCell(Row row, ColumnInfo columnInfo) {

        Cell cell = row.getCell(columnInfo.getColumnIndex());

        if (cell == null) {
            cell = row.createCell(columnInfo.getColumnIndex());
        }

        return cell;

    }

    /**
     * 转换数据对象的成员属性的值
     *
     * @param data
     * @return
     */
    private static String convertAttrMember(Object data) {

        if (data == null) {
            return "";
        }

        return convertValue(data);

    }

    /**
     * 获取指定名字的值
     *
     * @param dataMap    Excel的一行数据
     * @param columnInfo 单元格信息描述
     * @return
     */
    private static String fetchValue(Map<String, Object> dataMap, ColumnInfo columnInfo) {

        Object value = dataMap.get(columnInfo.getColumnName());

        if (value == null) {
            return columnInfo.getColumnDefaultValue();
        }

        if (columnInfo.getFormatType() == null) {
            return value.toString();
        }

        return FormatUtil.formatByDataType(columnInfo.getFormatType(), value, columnInfo.getFormatPattern());

    }

    /**
     * 获取单元格样式
     *
     * @param sheet      Excel的sheet
     * @param template   Excel模板
     * @param columnInfo 单元格描述信息
     * @return
     */
    private static CellStyle getCellStypeFirstDataRowReferColumn(Sheet sheet, ExcelTemplate template, ColumnInfo columnInfo) {

        CellStyle cellStyle = sheet.getRow(template.getBodyStartIndex()).getCell(columnInfo.getColumnIndex()).getCellStyle();

        if (cellStyle == null) {
            return makeDefaultCellStyle();
        }

        return cellStyle;

    }

    /**
     * 创建默认的单元格样式
     *
     * @return
     */
    private static CellStyle makeDefaultCellStyle() {

        //todo

        return null;

    }

    /**
     * 标题样式
     *
     * @param workbook Excel对象
     * @return
     */
    private static CellStyle getCellStyleTitle(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        Font titleFont = workbook.createFont();
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);

        return style;

    }

    /**
     * 表头样式
     *
     * @param workbook Excel对象
     * @return
     */
    private static CellStyle getCellStyleHead(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font headerFont = workbook.createFont();
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);

        return style;

    }


    /**
     * 表体样式
     *
     * @param workbook Excel对象
     * @return
     */
    private static CellStyle getCellStyleBody(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Font dataFont = workbook.createFont();
        dataFont.setFontName("宋体");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);

        return style;

    }

}
