package com.lll.learn.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lll.learn.pdf.GenoReportBuilder;
import com.lll.learn.pdf.GenoReportProxy;
import com.lll.learn.pdf.IReportBuilder;
import com.lll.learn.pdf.ReportBuilder;
import com.lll.learn.pdf.entity.PrintReportBean;
import com.lll.learn.pdf.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
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
    public void downLoad(@RequestParam("access_token") String token, @RequestParam("uuid") String uuid, @RequestParam(value = "part",defaultValue = "0") Integer part, HttpServletResponse response) throws IOException {
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
        // 构建PDF
        String outPath = "/Users/laoliangliang/Desktop/" + uuid + ".pdf";
        GenoReportBuilder builder = new GenoReportBuilder();
        builder.initPdf(outPath);
        builder.setPrintReportBean(data);
        builder.setPart(part);

        if (part == 0) {
            builder.buildAll(data);
        }else{
            invokePartProxy(builder);
        }

        // 输出
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + uuid + ".pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + uuid + ".pdf");
        FileUtil.writeToStream(new File(outPath), response.getOutputStream());

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "ms");
    }

    /**
     * 通过代理调用
     * @param builder
     */
    private void invokePartProxy(GenoReportBuilder builder) {
        InvocationHandler genoReportProxy = new GenoReportProxy(builder);
        IReportBuilder proxy = (IReportBuilder) Proxy.newProxyInstance(genoReportProxy.getClass().getClassLoader(), ReportBuilder.class.getInterfaces(), genoReportProxy);

        proxy.addIndex();
        proxy.addHello();
        proxy.addExaminee();
        proxy.addDetectionContent();
        proxy.addResultSummary();
        proxy.addContext();
        proxy.addThanks();
        proxy.addBackCover();
        proxy.addCatalog();
        proxy.addPageNumber();
    }
}
