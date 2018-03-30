package com.horrific.common.tools.downloadexcel.local;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.horrific.common.dto.table.TableViewVO;
import com.horrific.common.response.ServiceResponse;
import com.horrific.common.utils.DownloadExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wanghong12
 * @date 2018-3-28
 */

@Slf4j
public class DownloadTable {

    private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final String EXCELSUFFIX_XLSX = ".xlsx";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * return json to webPage
     * @param o
     * @param response
     * @throws Exception
     */
    public static void writeContentToPage(TableViewVO o, String title, HttpServletResponse response) throws Exception {
        if (o == null){
            byte[] data = toJson(ServiceResponse.failure("下载失败"));
            response.setContentType(DEFAULT_CONTENT_TYPE);
            ServletOutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        }else {
            TableViewVO tableVo = o;
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(getTitle(title), "UTF-8"));

            try (ServletOutputStream os = response.getOutputStream()) {
                SXSSFWorkbook workbook = DownloadExcelUtil.download(title, tableVo);
                workbook.write(os);
                os.flush();
                os.close();
                workbook.dispose();
                log.info("文件下载成功！-{}", title);
            } catch (Exception e) {
                log.error("Excel下载异常：错误信息-{}", e);
                throw e;
            }
        }

    }

    /**
     * 拼接excel标题
     *
     * @param title
     * @return
     */
    private static String getTitle(String title) {

        title = title.replace(EXCELSUFFIX_XLSX, "");

        return new StringBuilder(title)
                .append("-")
                .append(LocalDate.now().format(FORMATTER))
                .append(EXCELSUFFIX_XLSX)
                .toString();

    }

    /**
     * convert object to json via jackson
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] toJson(Object data) throws Exception {

        JsonFactory jsonFactory = new JsonFactory();

        jsonFactory.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

        ObjectMapper mapper = new ObjectMapper(jsonFactory);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        mapper.writeValue(bos, data);

        return bos.toByteArray();
    }
}
