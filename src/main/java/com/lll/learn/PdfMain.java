package com.lll.learn;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lll.learn.data.PrintReportBean;
import com.lll.learn.data.Result;
import com.lll.learn.pdf.GenoReportBuilder;
import com.lll.learn.pdf.ReportBuilder;
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
        ReportBuilder reportBuilder = new GenoReportBuilder();
        reportBuilder.initPdf("/Users/laoliangliang/Desktop/report.pdf");
        // 获取真实数据
//        HttpRequest request = HttpUtil.createPost(
//                "https://result.1genehealth.com/api/v1/biology/bio/report/print?access_token=d376b5fd-f2af-475b-973b-a07dac81d8dd&uuid=f0d041659cc34a84bb94f60245942bd6&mold=&type=1");

//        HttpRequest request = HttpUtil.createPost(
//                "http://result.dev.1genehealth.com/api/v1/biology/bio/report/print?access_token=421e6833-aafc-4640-9bc2-352331c1902e&uuid=5f60026ec7f1417a81be90d571415389&mold=&type=1");
//        HttpResponse execute = request.execute();
//        String body = execute.body();
//        Result<PrintReportBean> reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>(){});
        String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/request.json").getPath()), "UTF-8");
        Result<PrintReportBean> reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>(){});

        PrintReportBean data = reportBeanResult.getData();
        reportBuilder.setPrintReportBean(data);
        reportBuilder
                .addIndex()
                .addHello()
                .addExaminee()
                .addDetectionContent()
//                .addDirectory()
                .addResultSummary()
                .addContext()
                .addThanks()
                .addBackCover()
                .addCatalog()
        ;

        reportBuilder.build();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

}
