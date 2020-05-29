package com.onegene.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onegene.pdf.component.entity.PrintReportBean;
import com.onegene.pdf.component.entity.Result;
import com.onegene.pdf.component.GenoReportBuilder;
import com.onegene.pdf.component.AbstractReportBuilder;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 13:35
 **/
public class PdfMain {
    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AbstractReportBuilder abstractReportBuilder = new GenoReportBuilder();
        abstractReportBuilder.initPdf("/Users/laoliangliang/Desktop/report.pdf");
        // 获取真实数据
        int selected = 0;
        Result<PrintReportBean> reportBeanResult;
        if (selected == 1) {
            HttpRequest request = HttpUtil.createPost(
                    "http://result.dev.1genehealth.com/api/v1/biology/bio/report/print?access_token=0e7ad759-f724-4780-b549-24fea237c561&uuid=5f60026ec7f1417a81be90d571415389&mold=&type=1");
            HttpResponse execute = request.execute();
            String body = execute.body();
            reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>() {
            });
        } else {
            String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/request.json").getPath()), "UTF-8");
            reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>() {
            });
        }

        PrintReportBean data = reportBeanResult.getData();
        abstractReportBuilder.setPrintReportBean(data);
        abstractReportBuilder.buildAll(data);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

}
