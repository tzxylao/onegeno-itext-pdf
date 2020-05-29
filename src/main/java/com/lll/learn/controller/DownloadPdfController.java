package com.lll.learn.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lll.learn.pdf.GenoReportBuilder;
import com.lll.learn.pdf.entity.PrintReportBean;
import com.lll.learn.pdf.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/29 8:31
 **/
@RestController
@RequestMapping("/pdf")
public class DownloadPdfController {

    @Value("${onegene.url:http://result.1genehealth.com/api/v1/biology/bio/report/print}")
    private String url;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downLoad(@RequestParam("access_token") String token, @RequestParam("uuid") String uuid, HttpServletResponse response) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Map<String, Object> params = new HashMap<>();
        String body = HttpUtil.post(url + "?access_token=" + token + "&uuid=" + uuid, params);
        Result<PrintReportBean> reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>() {
        });
        if (reportBeanResult == null) {
            return;
        }
        PrintReportBean data = reportBeanResult.getData();
        if (data == null) {
            return;
        }
        GenoReportBuilder builder = new GenoReportBuilder();
        String outPath = "/Users/laoliangliang/Desktop/" + uuid + ".pdf";
        builder.initPdf(outPath);
        builder.buildAll(data);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + uuid + ".pdf");
        FileUtil.writeToStream(new File(outPath), response.getOutputStream());

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }
}
