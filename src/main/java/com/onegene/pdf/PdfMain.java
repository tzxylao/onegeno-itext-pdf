package com.onegene.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.onegene.pdf.component.AbstractReportBuilder;
import com.onegene.pdf.component.report.drug.AdultDrugReportBuilder;
import com.onegene.pdf.component.report.gene.GenoReportBuilder;
import com.onegene.pdf.component.entity.PrintReportBean;
import com.onegene.pdf.component.entity.Result;
import org.springframework.util.StopWatch;

import java.io.File;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/19 13:35
 **/
public class PdfMain {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String prefix = "/Users/laoliangliang/Desktop/";
        String fileName = "report.pdf";

        createDirectory(prefix);

        AbstractReportBuilder abstractReportBuilder = null;

        // 获取真实数据
        int selected = 2;
        if (selected < 2) {
            abstractReportBuilder = new GenoReportBuilder();
        }else if(selected == 2){
            abstractReportBuilder = new AdultDrugReportBuilder();
        }
        abstractReportBuilder.initPdf(prefix + fileName);
        Result<PrintReportBean> reportBeanResult = null;
        if (selected == 1) {
            HttpRequest request = HttpUtil.createPost(
                    "http://result.dev.1genehealth.com/api/v1/biology/bio/report/print?access_token=0e7ad759-f724-4780-b549-24fea237c561&uuid=5f60026ec7f1417a81be90d571415389&mold=&type=1");
            HttpResponse execute = request.execute();
            String body = execute.body();
            reportBeanResult = JSON.parseObject(body, new TypeReference<Result<PrintReportBean>>() {
            });
        } else if(selected == 0){
            String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/request.json").getPath()), "UTF-8");
            reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>() {
            });
        } else if (selected == 2) {
            String requestStr = FileUtil.readString(new File(PdfMain.class.getClassLoader().getResource("test/adult.json").getPath()), "UTF-8");
            reportBeanResult = JSON.parseObject(requestStr, new TypeReference<Result<PrintReportBean>>() {
            });
        }

        PrintReportBean data = reportBeanResult.getData();
        abstractReportBuilder.setPrintReportBean(data);
        abstractReportBuilder.buildAll(data);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

    private static void createDirectory(String prefixPath) {
        File prefixPathFile = new File(prefixPath + "temp/");
        if (!prefixPathFile.exists()) {
            prefixPathFile.mkdirs();
        }
    }

}
